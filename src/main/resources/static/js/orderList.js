

$(document).ready(function () { // 페이지가 로딩되는 순간 바로 실행
    console.log("ready!");
    orderListAjax(1); // 들어가서 바로 1페이지가 보임, 아래 function의 이름
});

function orderListAjax() { // 위에서 보낸 매개변수 1을 받아줌
    const innerHtml = $("#purchaseOderListForm") //orderListAjax.html 소스를 붙일 파일 위치 지정
    const f = document.getElementById("form1"); // orderList.html의 form1과 연결
    f.page.value = page;

    $.ajax({ // $부분이 jquery
        url: "/purchase_order_listAjax", //백엔드 경로
        type: 'GET',
        cache: false,
        data: $('#form1').serialize(), // 이걸 통해 input 애들 값을 백엔드 경로로 보내줌 그러면 백엔드가 받는다.
        dataType: "html",
        async: false,
        //성공 시에 orderList.html의 orderListForm태그 위치에 백엔드 경로(orderListAjax)에 연결 된 리턴 값인 프론트 orderListAjax.html을 넣어서 보여줌
        success: function (data) {
            $(innerHtml).html(data)

            setTimeout(function () {
            }, 1000)
        },
        error: function (e) {
            $(innerHtml).html("")
        }
    })

}


 