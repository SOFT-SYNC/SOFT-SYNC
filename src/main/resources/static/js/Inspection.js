

   function addToMainTable(button) {
      let orderNo = button.closest('tr').querySelector('td').innerText;
      fetchInspections(orderNo);
   }
   

   function fetchInspections(orderNo) {
	    fetch(`/api/inspections`, {
	        method: 'POST',
	        headers: {
	            'Content-Type': 'application/json'
	        },
	        body: JSON.stringify({ orderNo })
	    })
	    .then(response => response.json())
	    .then(data => {
	        populateMainTable(data.order);
	        populateInspecPlanList(data.inspectionList);
		       console.log(data); //inspection
	    })
	    .catch(error => console.error('Error fetching inspections:', error));
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
    
    // 데이터가 있는지 확인
    if (inspectionList && inspectionList.length > 0) {
        inspectionList.forEach(inspection => {
            let row = '';
            if (inspection.inspecNo) { // 데이터베이스에 값이 있는 경우
                row = `<tr>
                            <td><input type="text" name="inspecNo" value="${inspection.inspecNo}" readonly></td>
                            <td>${inspection.times}</td>
                            <td>${inspection.inspecPlan}</td>
                            <td>${inspection.inspecDate}</td>
                            <td>${inspection.orders.orderQuantity}</td>
                            <td>${inspection.quantity}</td>
                            <td><input type="text" name="percent" value="${inspection.percent}" readonly></td>
                            <td><button type="button" class="blueBtn" onclick="markComplete(this)">진행</button></td>
                        </tr>`;
            } else { // 데이터베이스에 값이 없는 경우
                row = `<tr>
                            <td><input type="text" name="inspecNo"></td>
                            <td><input type="text" name="times"></td>
                            <td><input type="text" name="inspecPlan"></td>
                            <td><input type="date" name="inspecDate"></td>
                            <td><input type="text" name="orderQuantity"></td>
                            <td><input type="text" name="quantity"></td>
                            <td><input type="text" name="percent"></td>
                            <td><button type="button" class="blueBtn" onclick="markComplete(this)">진행</button></td>
                        </tr>`;
            }
            inspecPlanListBody.innerHTML += row;
            console.log(inspection);
        });
    } else {
        // 데이터가 없는 경우 처리
        console.log("No data exists.");
        // 여기에 데이터가 없을 때 표시하는 추가적인 로직을 추가할 수 있습니다.
    }
}



//********************차수 등록 **********************************
   function registerInspection() {
      let orderNo = document.querySelector('#mainTable tbody tr:first-child td:first-child').innerText;
      let times = document.querySelector('#times').value;
      let inspecPlan = document.querySelector('#duedate').value;
      let quantity = document.querySelector('#quantity').value;

      fetch(`/api/inspections/create`, {
         method: 'POST',
         headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
         },
         body: `orderNo=${orderNo}&inspecPlan=${inspecPlan}&quantity=${quantity}&times=${times}`
      })
      .then(response => response.json())
      .then(data => {
         alert('새로운 검수 계획이 등록되었습니다.');
         fetchInspections(orderNo);
      })
      .catch(error => console.error('Error registering inspection:', error));
   }
   
   

   
   
   
 


//************************진행률 계산*****************
  
  function markComplete(button) {
    var row = button.closest("tr");
    var 발주량 = parseInt(row.cells[4].innerText);
    var 생산량 = parseInt(row.cells[5].innerText);

    if (isNaN(발주량) || isNaN(생산량)) {
        alert("발주량 또는 생산량이 없습니다.");
        return;
    }

    var 진행률 = (생산량 / 발주량) * 100;
    var 현재날짜 = new Date().toISOString().split('T')[0]; // 현재 날짜
    var 진행률값 = 진행률.toFixed(2); // 진행률 계산

    // 해당 셀에 input 요소가 있는 경우 값을 업데이트하고, 없는 경우 텍스트로 처리
    if (row.cells[3].querySelector('input[type="date"]')) {
        row.cells[3].querySelector('input[type="date"]').value = 현재날짜;
    } else {
        row.cells[3].innerText = 현재날짜;
    }

    if (row.cells[6].querySelector('input[type="text"]')) {
        row.cells[6].querySelector('input[type="text"]').value = 진행률값;
    } else {
        row.cells[6].innerText = 진행률값;
    }
}