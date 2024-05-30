

   function addToMainTable(button) {
      let orderNo = button.closest('tr').querySelector('td').innerText;
      fetchInspections(orderNo);
   }
   

function fetchInspections(orderNo) {
    // Ajax 요청을 보내기 위한 객체 생성
    var xhr = new XMLHttpRequest();
    var url = `/api/inspections`;
    
    // POST 메서드로 요청을 보내기 위한 설정
    xhr.open('POST', url, true);
    
    // 요청 헤더 설정
    xhr.setRequestHeader('Content-Type', 'application/json');

    // 요청 완료 시 콜백 함수 설정
    xhr.onload = function() {
        if (xhr.status === 200) {
            // 성공적으로 데이터를 받아왔을 때의 처리
            var data = JSON.parse(xhr.responseText);
            populateMainTable(data.order);
            populateInspecPlanList(data.inspectionList);
            console.log(data.inspectionList); //inspection
        } else {
            // 요청이 실패했을 때의 처리
            console.error('Error fetching inspections:', xhr.statusText);
        }
    };

    // 요청이 실패했을 때의 처리
    xhr.onerror = function() {
        console.error('Error fetching inspections:', xhr.statusText);
    };

    // 요청 본문에 데이터 추가하여 요청 보내기
    var requestBody = JSON.stringify({ orderNo });
    xhr.send(requestBody);
}



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
	}


function populateInspecPlanList(inspectionList) {
    const inspecPlanListBody = document.querySelector('#createtb tbody');
    inspecPlanListBody.innerHTML = '';

    if (!inspectionList || inspectionList.length === 0) {
        console.log("검수 정보가 없습니다.");
        return;
    }

    inspectionList.forEach(inspection => {
        let row = '';
        if (inspection.inspecNo) {
            row = `<tr>
                        <td><input type="text" name="inspecNo" value="${inspection.inspecNo}" readonly></td>
                        <td>${inspection.times}</td>
                        <td>${inspection.inspecPlan}</td>
                        <td>${inspection.inspectionList.inspecDate ? inspection.inspectionList.inspecDate : '-'}</td>
                        <td>${inspection.orders ? inspection.orders.orderQuantity : '-'}</td>
                        <td>${inspection.quantity ? inspection.quantity : '-'}</td>
                        <td><input type="text" name="percent" value="${inspection.inspectionList.percent ? inspection.inspectionList.percent : '-'}" readonly></td>
                        <td>${inspection.inspectionList.inspecYn === 'Y' ? '<button type="button" class="blueBtn" disabled>완료</button>' : '<button type="button" class="blueBtn" onclick="markComplete(this)">검수확정</button>'}</td>
                    </tr>`;
        } 
        inspecPlanListBody.innerHTML += row;
        console.log(inspection);
    });
}
//********************차수 등록 **********************************
  function registerInspection() {
    let orderNo = document.querySelector('#mainTable tbody tr:first-child td:first-child').innerText;
    let times = document.querySelector('#times').value;
    let inspecPlan = document.querySelector('#duedate').value;
    let quantity = document.querySelector('#quantity').value;

    fetch(`/saveInspection`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `inspecNo=${orderNo}&percent=${quantity}`
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('검수 등록 중에 문제가 발생했습니다.');
        }
        return response.json();
    })
    .then(data => {
        alert('새로운 검수 계획이 등록되었습니다.');
        fetchInspections(orderNo);
    })
    .catch(error => {
        console.error('Error registering inspection:', error);
        alert('검수 등록 중에 문제가 발생했습니다.');
    });
}

function markComplete(button) {
    var row = button.closest("tr");
    var inspecNo = row.cells[0].querySelector('input[name="inspecNo"]').value; // 검수번호 가져오기
    var quantity = parseInt(row.cells[5].innerText);

    if (isNaN(quantity)) {
        alert("생산량이 없습니다.");
        return;
    }

    var formData = new FormData();
    formData.append('inspecNo', inspecNo);
    formData.append('percent', quantity);

    fetch('/saveInspection', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('검수 등록 중에 문제가 발생했습니다.');
        }
        alert('검수가 완료되었습니다.');
    })
    .catch(error => {
        console.error('Error registering inspection:', error);
        alert('검수 등록 중에 문제가 발생했습니다.');
    });
}
   
   
   
 
