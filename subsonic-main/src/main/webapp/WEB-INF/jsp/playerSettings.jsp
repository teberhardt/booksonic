<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="iso-8859-1" %>
<%--@elvariable id="command" type="net.sourceforge.subsonic.command.PlayerSettingsCommand"--%>

<html><head>
    <%@ include file="head.jsp" %>
    <%@ include file="jquery.jsp" %>
    <script type="text/javascript" src="<c:url value="/script/scripts-2.0.js"/>"></script>
</head>
<body class="mainframe bgcolor1">
<script type="text/javascript" src="<c:url value="/script/wz_tooltip.js"/>"></script>
<script type="text/javascript" src="<c:url value="/script/tip_balloon.js"/>"></script>

<c:set var="category" value="player"/>
<c:set var="toast" value="${command.reloadNeeded}"/>
<c:set var="user" value="${command.user}"/>
<%@ include file="settingsHeader.jsp" %>

<fmt:message key="common.unknown" var="unknown"/>

<c:choose>
    <c:when test="${empty command.players}">
        <p><fmt:message key="playersettings.noplayers"/></p>
    </c:when>
    <c:otherwise>

        <c:url value="playerSettings.view" var="deleteUrl">
            <c:param name="delete" value="${command.playerId}"/>
        </c:url>
        <c:url value="playerSettings.view" var="cloneUrl">
            <c:param name="clone" value="${command.playerId}"/>
        </c:url>

        <table class="indent">
            <tr>
                <td><b><fmt:message key="playersettings.title"/></b></td>
                <td>
                    <select name="player" onchange="location='playerSettings.view?id=' + options[selectedIndex].value;">
                        <c:forEach items="${command.players}" var="player">
                            <option ${player.id eq command.playerId ? "selected" : ""}
                                    value="${player.id}">${fn:escapeXml(player.description)}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="padding-right:1em"><i class="fa fa-chevron-right icon"></i>&nbsp;<a href="${deleteUrl}"><fmt:message key="playersettings.forget"/></a></td>
                <td><i class="fa fa-chevron-right icon"></i>&nbsp;<a href="${cloneUrl}"><fmt:message key="playersettings.clone"/></a></td>
            </tr>
        </table>

        <form:form commandName="command" method="post" action="playerSettings.view">
            <form:hidden path="playerId"/>

            <table class="ruleTable indent">
                <c:forEach items="${command.technologyHolders}" var="technologyHolder">
                    <c:set var="technologyName">
                        <fmt:message key="playersettings.technology.${fn:toLowerCase(technologyHolder.name)}.title"/>
                    </c:set>

                    <tr>
                        <td class="ruleTableHeader">
                            <form:radiobutton id="radio-${technologyName}" path="technologyName" value="${technologyHolder.name}"/>
                            <b><label for="radio-${technologyName}">${technologyName}</label></b>
                        </td>
                        <td class="ruleTableCell" style="width:40em">
                            <fmt:message key="playersettings.technology.${fn:toLowerCase(technologyHolder.name)}.text"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <table class="indent" style="border-spacing:3pt;">
                <tr>
                    <td style="padding-right: 1.5em"><fmt:message key="playersettings.type"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${empty command.type}">${unknown}</c:when>
                            <c:otherwise>${command.type}</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <td style="padding-right: 1.5em"><fmt:message key="playersettings.lastseen"/></td>
                    <td><fmt:formatDate value="${command.lastSeen}" type="both" dateStyle="long" timeStyle="medium"/></td>
                </tr>
            </table>

            <table class="indent" style="border-spacing:3pt;">

                <tr>
                    <td><fmt:message key="playersettings.name"/></td>
                    <td><form:input path="name" size="16"/></td>
                    <td colspan="2"><c:import url="helpToolTip.jsp"><c:param name="topic" value="playername"/></c:import></td>
                </tr>

                <tr>
                    <td><fmt:message key="playersettings.maxbitrate"/></td>
                    <td>
                        <form:select path="transcodeSchemeName" cssStyle="width:8em">
                            <c:forEach items="${command.transcodeSchemeHolders}" var="transcodeSchemeHolder">
                                <form:option value="${transcodeSchemeHolder.name}" label="${transcodeSchemeHolder.description}"/>
                            </c:forEach>
                        </form:select>
                    </td>
                    <td>
                        <c:import url="helpToolTip.jsp"><c:param name="topic" value="transcode"/></c:import>
                    </td>
                    <td class="warning">
                        <c:if test="${not command.transcodingSupported}">
                            <fmt:message key="playersettings.notranscoder"/>
                        </c:if>
                    </td>
                </tr>

            </table>

            <table class="indent" style="border-spacing:3pt">

        <tr>
            <td>
                <form:checkbox path="dynamicIp" id="dynamicIp" cssClass="checkbox"/>
                <label for="dynamicIp"><fmt:message key="playersettings.dynamicip"/></label>
            </td>
            <td><c:import url="helpToolTip.jsp"><c:param name="topic" value="dynamicip"/></c:import></td>
        </tr>

        <tr>
            <td>
                <form:checkbox path="autoControlEnabled" id="autoControlEnabled" cssClass="checkbox"/>
                <label for="autoControlEnabled"><fmt:message key="playersettings.autocontrol"/></label>
            </td>
            <td><c:import url="helpToolTip.jsp"><c:param name="topic" value="autocontrol"/></c:import></td>
        </tr>
    </table>

    <input type="submit" value="<fmt:message key="common.save"/>" style="margin-top:1em;margin-right:0.3em">
    <input type="button" value="<fmt:message key="common.cancel"/>" style="margin-top:1em" onclick="location.href='nowPlaying.view'">
</form:form>

</c:otherwise>
</c:choose>

<c:if test="${command.reloadNeeded}">
    <script language="javascript" type="text/javascript">parent.frames.playQueue.location.href="playQueue.view?"</script>
</c:if>

</body></html>