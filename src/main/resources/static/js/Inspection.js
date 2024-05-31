

   function addToMainTable(button) {
      let orderNo = button.closest('tr').querySelector('td').innerText;
      fetchInspections(orderNo);
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
        console.log(data);
    })
    .catch(error => {
        console.error('Error fetching inspections:', error);
    });
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
        alert('새로운 검수 계획이 등록되었습니다.');

        // 새로운 검수 계획을 createtb 테이블에 추가
        addNewInspectionToTable(data);
    })
    .catch(error => {
        console.error('Error registering inspection:', error);
        alert('검수 등록 중에 문제가 발생했습니다.');
    });
}

function addNewInspectionToTable(inspection) {
    const inspecPlanListBody = document.querySelector('#createtb tbody');
    
    let row = '<tr>' +
                '<td><input type="text" name="inspecNo" value="' + inspection.inspecNo + '" readonly></td>' +
                '<td>' + inspection.times + '</td>' +
                '<td>' + inspection.inspecPlan + '</td>' +
                '<td>' + (inspection.inspecDate ? inspection.inspecDate : '-') + '</td>' +
                '<td>' + (inspection.orders ? inspection.orders.orderQuantity : '-') + '</td>' +
                '<td>' + (inspection.quantity ? inspection.quantity : '-') + '</td>' +
                '<td><input type="text" name="percent" value="' + (inspection.percent ? inspection.percent : '-') + '" readonly></td>';

    if (inspection.inspecYn === 'Y') {
        row += '<td><button type="button" class="blueBtn" disabled>완료</button></td>';
    } else {
        row += '<td><button type="button" class="blueBtn" onclick="markComplete(this, ' + inspection.inspecNo + ')">검수확정</button></td>';
    }
    
    row += '</tr>';
    
    inspecPlanListBody.innerHTML += row;
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
    
    var formData = new FormData();
    formData.append('inspecNo', inspecNo);
    formData.append('percent', percent);

    fetch('/saveInspection', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('검수 등록 중에 문제가 발생했습니다.');
        }
    })
    .then(data => {
        // 검수가 완료되면 해당 행의 데이터를 업데이트하고 버튼을 변경
        //row.querySelector('input[name="percent"]').setAttribute('readonly', 'readonly');
       // row.querySelector('button').setAttribute('disabled', 'disabled');
        //row.querySelector('button').innerText = '완료';
        alert('검수가 완료되었습니다.');
    })
    .catch(error => {
        console.error('Error registering inspection:', error);
        alert('검수 등록 중에 문제가 발생했습니다.');
    });
}
   
   
   
 
