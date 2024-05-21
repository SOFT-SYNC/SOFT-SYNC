//js를 통해 리스트와 아작스를 연결다

$(document).ready(function () { // 페이지가 로딩되는 순간 바로 실행
    console.log("ready!");
    reservationListAjax(1); // 들어가서 바로 1페이지가 보임, 아래 function의 이름
});

function reservationListAjax() { // 위에서 보낸 매개변수 1을 받아줌
    const innerHtml = $("#orderListForm") //소스를 붙일 파일 위치 지정
    const f = document.getElementById("form1"); 
    f.page.value = page;

    $.ajax({ // $부분이 jquery
        url: "/purchase_order", //백엔드 경로
        type: 'GET',
        cache: false,
        data: $('#form1').serialize(), 
        dataType: "html",
        async: false,
        success: function (data) {
            $(innerHtml).html(data)

            setTimeout(function () {
            }, 1000)
        },
        error: function (e) {
            $(innerHtml).html("")
        }
    })
