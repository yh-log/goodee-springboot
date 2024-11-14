<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>러닝크루 등록</title>
    <link rel="stylesheet" href="/css/figma.css/vars.css">
    <link rel="stylesheet" href="/css/figma.css/crewWrite.css">
</head>
<body>
	<header class="header">
	    <div class="header-logo">
	        <h3>이런저런</h3>
	    </div>
	    <div class="header-menu">
	        <nav class="menu">
	            <ul>
	                <li>러닝크루</li>
	                <li>러닝메이트</li>
	                <li>게시판</li>
	                <li>아이콘몰</li>
	                <li>문의하기</li>
	            </ul>
	        </nav>
	    </div>
	    <div class="header-icon">
	        <!-- 추후 추가할 아이콘 자리 -->
	        <span>🔔</span>
	        <span>🔔</span>
	    </div>
	</header>

  <main class="registration-container">
    <section class="form-section">
      <form id="crew-registration-form">
        <label for="crew-name">크루 명</label>
        <input type="text" id="crew-name" name="crew-name">

        <label for="tags">태그</label>
        <input type="text" id="tags" name="tags">

        <div class="form-row">
          <div>
            <label for="day">요일</label>
            <select id="day" name="day">
              <option value="mon">월</option>
              <option value="tue">화</option>
              <option value="wed">수</option>
              <option value="thu">목</option>
              <option value="fri">금</option>
              <option value="sat">토</option>
              <option value="sun">일</option>
            </select>
          </div>
          <div>
            <label for="intensity">운동 강도</label>
            <input type="number" id="intensity" name="intensity">
          </div>
        </div>

        <label for="region">지역</label>
        <input type="text" id="region" name="region">

        <label for="crew-description">크루 설명</label>
        <textarea id="crew-description" name="crew-description"></textarea>

        <div class="button-group">
          <button type="submit" class="primary-button">러닝크루 등록</button>
          <button type="button" class="secondary-button">등록 취소하기</button>
        </div>

        <div class="tag-group">
          <button type="button" class="tag-button">🏃‍♂️ 러닝에 집중</button>
          <button type="button" class="tag-button">🙋‍♀️ 친목도 중요</button>
          <button type="button" class="tag-button">💦 러닝 고수만</button>
          <button type="button" class="tag-button">🥳 초보도 환영</button>
          <button type="button" class="tag-button">🐂 소규모 크루</button>
          <button type="button" class="tag-button">남성만 가능</button>
          <button type="button" class="tag-button">여성만 가능</button>
        </div>
      </form>
    </section>
  </main>

  <footer class="footer">
    <div class="menu-section">
      <div>MENU</div>
      <div>러닝크루</div>
      <div>아이콘몰</div>
      <div>게시판</div>
    </div>
    <div class="contact-section">
      <p>운영시간 : 평일 09:00-18:00 (주말 및 공휴일 휴무)</p>
      <p>주소 : 서울 강남구 은달래로 19 스퀘어9</p>
      <p>E-MAIL : info@korbit.co.kr | TEL : 070-0707-0707</p>
      <p>Copyright © ARCHIVIO All Rights Reserved.</p>
    </div>
  </footer>
</body>
</html>
