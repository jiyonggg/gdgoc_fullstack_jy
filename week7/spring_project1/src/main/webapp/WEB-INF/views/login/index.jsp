<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그인 페이지</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/login/style.css">
</head>
<body>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>

    <div id="login-container-wrapper">
        <div id="login-container">
            <h2>로그인</h2>
            <%-- localhost:8080/login 으로 로그인 데이터 넘어감 --%>
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="input-group">
                    <label for="username">아이디</label>
                    <%-- input 태그의 name: 백으로 데이터를 보낼 때의 키 값 --%>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="input-group">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit" id="login-button">로그인</button>
            </form>
            <div id="register-link">
                <%-- a: 페이지 이동(링크) --%>
                <%-- pageContext.request.contextPath: 최상위(루트) 경로 (localhost:8080) --%>
                <%-- 즉, localhost:8000/register --%>
                <a href="${pageContext.request.contextPath}/register">회원가입</a>
            </div>
        </div>
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>