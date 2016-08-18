<jsp:useBean id="mappingDirectoryMappingManager" scope="session" class="fr.paris.lutece.plugins.directory.modules.mappingmanager.web.DirectoryMappingManagerJspBean" />
<% String strContent = mappingDirectoryMappingManager.processController ( request , response ); %>

<%@ page errorPage="../../../../ErrorPage.jsp" %>
<jsp:include page="../../../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../../../AdminFooter.jsp" %>
