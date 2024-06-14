	
function endInspection(button) {
    // 버튼을 클릭하여 해당 주문번호 가져오기
    let orderNo = button.closest('tr').querySelector('td').innerText;
    console.log(orderNo);
    // 서버로 데이터를 전송하기 위한 fetch 요청 보내기
    fetch('/endInspections', {
        method: 'POST', // POST 방식으로 요청
        headers: {
            'Content-Type': 'application/json' // JSON 형식의 데이터 전송
        },
        body: JSON.stringify({ orderNo: orderNo }) // 주문번호를 JSON 형식으로 변환하여 body에 포함
    });
}




   function addToMainTable(button) {
      let orderNo = button.closest('tr').querySelector('td').innerText;
      fetchInspections(orderNo);
      
          var row = button.closest('tr');
    
    // 발주일 값을 가져옵니다.
    var orderDate = row.querySelector('td:nth-child(2)').innerText;

    // 진척검수 일정 등록 테이블의 검수예정일 입력 필드를 찾습니다.
    var inspectionDateInput = document.getElementById('duedate');

    // 발주일을 Date 객체로 변환합니다.
    var orderDateObj = new Date(orderDate);
    
    // 검수예정일에 설정할 최소값을 계산합니다.
    var minInspectionDate = new Date(orderDateObj.getTime());
    var minInspectionDateString = minInspectionDate.toISOString().split('T')[0];
    
    // 검수예정일 입력 필드에 최소값을 설정합니다.
    inspectionDateInput.setAttribute('min', minInspectionDateString);

    // 검수예정일 입력 필드를 활성화합니다.
    inspectionDateInput.disabled = false;
      
      
   }
   

function fetchInspections(orderNo) {
    var url = `/api/inspections`;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ orderNo: orderNo })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        populateMainTable(data.order);
        populateInspecPlanList(data.inspectionList);
       
    })
    .catch(error => {
        console.error('Error fetching inspections:', error);
    });
}

//일정 등록 리스트 1개 추가함.
   function populateMainTable(order) {
	    const mainTableBody = document.querySelector('#mainTable tbody');
	    mainTableBody.innerHTML = ''; 
	    // 입고예정일 to yyyy-MM-dd
	    const receiveDuedate = new Date(order.receiveDuedate).toISOString().split('T')[0];
	    let row = `<tr>
	                <td>${order.orderNo}</td>
	                <td>${order.orderDate}</td>
	                <td>${receiveDuedate}</td>
	                <td>${order.item.itemCode}</td>
	                <td>${order.item.itemName}</td>
	                <td>${order.orderQuantity}</td>
	              </tr>`;
	    mainTableBody.innerHTML += row;
	    
	    var inspectionDateInput = document.getElementById('duedate');
		var maxInspectionDate = new Date(receiveDuedate);
		inspectionDateInput.setAttribute('max', receiveDuedate);
	}

function populateInspecPlanList(inspectionList) {
console.log(inspectionList +"검수리스트"); // inspectionList를 콘솔에 출력하여 확인
    const createtbBody = document.querySelector('#createtb tbody');
    createtbBody.innerHTML = '';

    inspectionList.forEach(inspection => {
        let inspecDate = inspection.inspecDate ? new Date(inspection.inspecDate).toISOString().split('T')[0] : '-'; // 검수일 변환
        let percent = inspection.percent ? inspection.percent : '-'; // 진행률 확인

        let row = `<tr>
                    <td style="display:none;"><input type="text" name="inspecNo" value="${inspection.inspecNo}" readonly></td>
                    <td>${inspection.times}</td>
                    <td>${inspection.inspecPlan}</td>
                    <td>${inspecDate}</td>
                    <td>${inspection.orders ? inspection.orders.orderQuantity : '-'}</td>
                    <td>${inspection.quantity ? inspection.quantity : '-'}</td>
                    <td><input type="text" name="percent" value="${percent}" readonly></td>
                    <td>${inspection.inspecYn == 'Y' ? '<button type="button" class="blueBtn" disabled>완료</button>' : '<button type="button" class="blueBtn" onclick="markComplete(this, ' + inspection.inspecNo + ')">검수확정</button>'}</td>
                  </tr>`;
        createtbBody.innerHTML += row;
    });
}


//********************차수 등록 **********************************
function registerInspectionss() {
    // 입력값 가져오기
    var orderNo = document.querySelector('#mainTable tbody tr:first-child td:first-child').innerText;
    var times = document.getElementById('times').value;
    var inspecPlan = document.getElementById('duedate').value;
    var quantity = document.getElementById('quantity').value;

    // POST 요청 보내기
    fetch('/api/inspections/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'orderNo=' + encodeURIComponent(orderNo) + '&inspecPlan=' + encodeURIComponent(inspecPlan) + '&quantity=' + encodeURIComponent(quantity) + '&times=' + encodeURIComponent(times)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('검수 등록 중에 문제가 발생했습니다.');
        }
        return response.json();
    })
    .then(data => {
          if (Object.keys(data).length === 0 && data.constructor === Object) {
        // 반환된 데이터가 비어있을 때 알람창 표시
        alert('총 검수량이 발주량을 초과하여 검수를 등록할 수 없습니다.');
        
	    } else {
	        // 반환된 데이터가 비어있지 않을 때 처리
	        alert('새로운 검수 계획이 등록되었습니다.');
	     populateInspecPlanList(data.inspectionList);  //리스트 갱신
	       
	    }

    })
    .catch(error => {
        console.error('Error registering inspection:', error);
        alert('검수 등록 중에 문제가 발생했습니다.');
    });
    
    
}


function markComplete(button) {
    var row = button.closest("tr");
    var inspecNo = row.cells[0].querySelector('input[name="inspecNo"]').value; // 검수번호 가져오기
    var orderQuantity = parseInt(row.cells[4].innerText); // 발주량
    var quantity = parseInt(row.cells[5].innerText); // 생산량

    if (isNaN(orderQuantity) || isNaN(quantity)) {
        alert("발주량 또는 생산량이 유효하지 않습니다.");
        return;
    }

    // 진행률 계산
    var percent = (quantity / orderQuantity) * 100;

    // percent 값을 소수점 둘째 자리까지 반올림하여 소수점 이하 자리를 제한
    percent = Math.round(percent * 100) / 100;

    // percent 값을 문자열로 변환하여 % 기호를 추가
    percent = percent.toFixed(2) + "%";

    // 진행률 표시 열의 DOM 요소 찾기
    var percentCell = row.cells[6];
    // 진행률 값을 갱신
    percentCell.querySelector('input[name="percent"]').value = percent;

    var inspecDate = new Date().toISOString().split('T')[0];
    row.cells[3].innerText = inspecDate;

    // 서버로 데이터 저장 요청
    var formData = new FormData();
    formData.append('inspecNo', inspecNo);
    formData.append('percent', percent);
	formData.append('quantity', quantity);
	
    fetch('/saveInspection', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('검수 등록 중에 문제가 발생했습니다.');
        }
        return response.json(); // JSON 형식으로 응답을 파싱
    })
    .then(data => {
        alert('검수가 완료되었습니다.');
        populateInspecPlanList(data.inspectionList);
    })
    .catch(error => {
        console.error('Error registering inspection:', error);
        alert('검수 등록 중에 문제가 발생했습니다.');
    });
}