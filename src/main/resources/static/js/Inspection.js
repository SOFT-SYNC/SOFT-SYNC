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
        console.log("Data received from server: ", data);
        populateMainTable(data.order);
        populateInspecPlanList(data.inspectionList);
    })
    .catch(error => {
        console.error('Error fetching inspections:', error);
    });
}

function populateMainTable(order) {
    const mainTableBody = document.querySelector('#mainTable tbody');
    mainTableBody.innerHTML = ''; 
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
        let inspecDate = '-';
        let percent = '-';

        if (inspection.inspectionList.length > 0) {
            // inspectionList가 비어있지 않다면 값을 가져옴
            let inspecDetails = inspection.inspectionList[0];
            inspecDate = inspecDetails.inspecDate ? inspecDetails.inspecDate : '-';
            percent = inspecDetails.percent ? inspecDetails.percent : '-';
        }

        row = `<tr>
                    <td><input type="text" name="inspecNo" value="${inspection.inspecNo}" readonly></td>
                    <td>${inspection.times}</td>
                    <td>${inspection.inspecPlan}</td>
                    <td>${inspection.inspecYn === 'Y' ? inspecDate : '-'}</td>
                    <td>${inspection.orders ? inspection.orders.orderQuantity : '-'}</td>
                    <td>${inspection.quantity ? inspection.quantity : '-'}</td>
                    <td><input type="text" name="percent" value="${inspection.inspecYn === 'Y' ? percent : '-'}" readonly></td>
                    <td>${inspection.inspecYn === 'Y' ? '<button type="button" class="blueBtn" disabled>완료</button>' : '<button type="button" class="blueBtn" onclick="markComplete(this)">검수확정</button>'}</td>
                </tr>`;
        
        inspecPlanListBody.innerHTML += row;
        console.log(inspection);
    });
}

function registerInspectionss() {
    var orderNo = document.querySelector('#mainTable tbody tr:first-child td:first-child').innerText;
    var times = document.getElementById('times').value;
    var inspecPlan = document.getElementById('duedate').value;
    var quantity = document.getElementById('quantity').value;

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
        addNewInspectionToTable(data);
    })
    .catch(error => {
        console.error('Error registering inspection:', error);
        alert('검수 등록 중에 문제가 발생했습니다.');
    });
}

function addNewInspectionToTable(inspection) {
    const inspecPlanListBody = document.querySelector('#createtb tbody');
    
    let inspecDate = '-';
    let percent = '-';

    if (inspection.inspectionList && inspection.inspectionList.length > 0) {
        let inspecDetails = inspection.inspectionList[0];
        inspecDate = inspecDetails.inspecDate ? inspecDetails.inspecDate : '-';
        percent = inspecDetails.percent ? inspecDetails.percent : '-';
    }

    let row = `<tr>
                    <td><input type="text" name="inspecNo" value="${inspection.inspecNo}" readonly></td>
                    <td>${inspection.times}</td>
                    <td>${inspection.inspecPlan}</td>
                    <td>${inspection.inspecYn === 'Y' ? inspecDate : '-'}</td>
                    <td>${inspection.orderQuantity ? inspection.orderQuantity : '-'}</td>
                    <td>${inspection.quantity ? inspection.quantity : '-'}</td>
                    <td><input type="text" name="percent" value="${inspection.inspecYn === 'Y' ? percent : '-'}" readonly></td>
                    <td>${inspection.inspecYn === 'Y' ? '<button type="button" class="blueBtn" disabled>완료</button>' : '<button type="button" class="blueBtn" onclick="markComplete(this)">검수확정</button>'}</td>
                </tr>`;
    
    inspecPlanListBody.innerHTML += row;
}

function markComplete(button) {
    var row = button.closest("tr");
    var inspecNo = row.cells[0].querySelector('input[name="inspecNo"]').value;
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
        return response.json();
    })
    .then(data => {
        alert('검수가 완료되었습니다.');
        updateInspectionRow(row, data);
    })
    .catch(error => {
        console.error('Error registering inspection:', error);
        alert('검수 등록 중에 문제가 발생했습니다.');
    });
}

function updateInspectionRow(row, inspection) {
    let inspecDate = '-';
    let percent = '-';

    if (inspection.inspectionList && inspection.inspectionList.length > 0) {
        let inspecDetails = inspection.inspectionList[0];
        inspecDate = inspecDetails.inspecDate ? inspecDetails.inspecDate : '-';
        percent = inspecDetails.percent ? inspecDetails.percent : '-';
    }

    row.cells[3].innerText = inspection.inspecYn === 'Y' ? inspecDate : '-';
    row.cells[6].querySelector('input[name="percent"]').value = inspection.inspecYn === 'Y' ? percent : '-';
    row.cells[7].innerHTML = '<button type="button" class="blueBtn" disabled>완료</button>';
}
