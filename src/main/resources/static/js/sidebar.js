  function toggleSidenav() {
      const sidenav = document.querySelector('.sidenav');
      const mainContent = document.querySelector('.main-content');
      const sidenavToggle = document.querySelector('.sidenav-toggle');
  
      if (sidenav.style.left === '0px') {
          sidenav.style.left = '-200px'; // 사이드바 숨기기
          mainContent.style.marginLeft = '0'; // 메인 콘텐츠 원래 위치로
          sidenavToggle.style.left = '5px'; // 토글 버튼 원래 위치로
      } else {
          sidenav.style.left = '0'; // 사이드바 보이기
          mainContent.style.marginLeft = '200px'; // 메인 콘텐츠를 오른쪽으로 밀어냄
          sidenavToggle.style.left = '205px'; // 토글 버튼 오른쪽으로 이동
      }
  }
  
 
  document.addEventListener('DOMContentLoaded', function() {
	    // 드롭다운 버튼을 모두 선택합니다.
	    var dropdowns = document.querySelectorAll('.dropdown-btn');

	    // 각 버튼에 클릭 이벤트 리스너를 추가합니다.
	    dropdowns.forEach(function(btn) {
	        btn.addEventListener('click', function() {
	            var dropdownContainer = this.nextElementSibling;
	            var isOpen = dropdownContainer.style.display === 'block';
	            
	            // 현재 클릭된 드롭다운을 열거나 닫습니다.
	            dropdownContainer.style.display = isOpen ? 'none' : 'block';

	            // 클릭된 버튼에 'active' 클래스를 추가합니다.
	            this.classList.toggle('active');
	        });
	    });

	    // 문서 전체를 클릭할 때 모든 드롭다운을 닫는 리스너를 추가합니다.
	    document.addEventListener('click', function(event) {
	        // 클릭된 요소가 드롭다운 버튼 또는 드롭다운 컨테이너인지 확인합니다.
	        var isDropdownButton = event.target.classList.contains('dropdown-btn');
	        var isDropdownContainer = event.target.classList.contains('dropdown-container');

	        // 클릭된 요소가 드롭다운 버튼 또는 드롭다운 컨테이너가 아니라면 모든 드롭다운을 닫습니다.
	        if (!isDropdownButton && !isDropdownContainer) {
	            closeAllDropdowns();
	        }
	    });

	    // 모든 드롭다운을 닫는 함수
	    function closeAllDropdowns() {
	        var dropdownContainers = document.querySelectorAll('.dropdown-container');
	        dropdownContainers.forEach(function(container) { 
	            container.style.display = 'none';
	        });

	        // 모든 드롭다운 버튼에서 'active' 클래스를 제거합니다.
	        dropdowns.forEach(function(btn) {
	            btn.classList.remove('active');
	        });
	    }
	});