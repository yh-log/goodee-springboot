function setCookie(name, value, days) {
    const date = new Date();
    date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
    document.cookie = `${name}=${value}; expires=${date.toUTCString()}; path=/`;
}

function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length == 2) {
        const cookieValue = parts.pop().split(';').shift();
        console.log(`Cookie Value for ${name}: ${cookieValue}`); // 쿠키 값 확인
        return cookieValue;
    }
    return null;
}


function closePopup(popupId) {
    setCookie(`hidePopup_${popupId}`, 'true', 1);
    const popupElement = document.getElementById(`popup_${popupId}`);
    if (popupElement) {
        popupElement.style.display = 'none';
        console.log(`Popup ${popupId} is set to display: none`); // display 설정 확인
    }
}

document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll('[id^="popup_"]').forEach(popup => {
        const popupId = popup.id.split('_')[1];
        if (getCookie(`hidePopup_${popupId}`) === 'true') {
            popup.style.display = 'none';
            console.log(`Popup ${popupId} is hidden on load due to cookie`);
        } else {
            console.log(`Popup ${popupId} is visible`);
        }
    });
});


