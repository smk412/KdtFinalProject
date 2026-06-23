// 1. 편집 모드 활성화 (일반 모드 숨기고 편집 모드 보이기)
function enableEditMode(button) {
    const header = button.closest('.milestone-header');
    header.querySelector('.view-mode-container').style.display = 'none';
    header.querySelector('.edit-mode-container').style.display = 'flex'; // 혹은 블록스타일에 맞게 지정
}

// 2. 편집 모드 취소 (편집 모드 숨기고 일반 모드 되돌리기)
function cancelEditMode(button) {
    const header = button.closest('.milestone-header');
    header.querySelector('.edit-mode-container').style.display = 'none';
    header.querySelector('.view-mode-container').style.display = 'flex';
}

// 3. 변경 버튼 클릭 시 서버로 비동기 수정(AJAX) 요청
function saveMilestone(button) {
    const header = button.closest('.milestone-header');
    const milestoneId = header.getAttribute('data-milestone-id');
    
    // 수정된 값 추출
    const updatedTitle = header.querySelector('.edit-title-input').value;
    const updatedDate = header.querySelector('.edit-date-input').value;
    const isChecked = header.querySelector('.edit-status-toggle').checked;
    const updatedStatus = isChecked ? 'g2' : 'g1';

    if(!updatedTitle.trim()) {
        alert("마일스톤명을 입력해주세요.");
        return;
    }

    // 서버 전송 데이터 구조화
    const data = {
        milestoneId: milestoneId,
        milestoneTitle: updatedTitle,
        finishDate: updatedDate,
        milestoneStatus: updatedStatus
    };

    // REST 형태로 Controller에 전송 (@PostMapping("/update"))
    // 💡 팁: 현재 컨트롤러 수정 로직이 application/x-www-form-urlencoded 형식이므로 URLSearchParams 처리를 하거나 컨트롤러에 @RequestBody 조율이 필요합니다. 
    // 여기서는 기존 컨트롤러 규격에 맞춰 FormData 형태로 전省합니다.
    const formData = new URLSearchParams();
    formData.append('milestoneId', milestoneId);
    formData.append('projectId', new URLSearchParams(window.location.search).get('projectId')); // 쿼리스트링에서 projectId 추출
    formData.append('milestoneTitle', updatedTitle);
    formData.append('finishDate', updatedDate);
    formData.append('milestoneStatus', updatedStatus);

    fetch('/project/milestone/update', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
    })
    .then(response => {
        if (response.ok) {
            alert('성공적으로 변경되었습니다.');
            location.reload(); // 간편하게 화면 새로고침하여 반영 데이터 반영
        } else {
            alert('변경에 실패했습니다.');
        }
    })
    .catch(error => console.error('Error:', error));
}