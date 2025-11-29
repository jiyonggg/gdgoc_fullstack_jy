<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>카네스블랙 카페</title>
</head>
<body>
    <%-- include: JSP에 있던 코드가 모두 들어감 --%>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>

    <div id="container">
        <div id="menuAdmin">
            <h2 id="menuAdminH2">공지사항</h2>
            <div id="menuList">
            </div>
        </div>
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</body>
</html>