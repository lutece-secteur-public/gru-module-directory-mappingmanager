<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="managedirectorymappingmanager" scope="session" class="fr.paris.lutece.plugins.directory.modules.mappingmanager.web.ManageDirectoryMappingmanagerJspBean" />

<% managedirectorymappingmanager.init( request, managedirectorymappingmanager.RIGHT_DIRECTORYMAPPINGMANAGER_MANAGEMENT ); %>
<%= managedirectorymappingmanager.getManageModulenotifygrumappingmanagerHome ( request ) %>

<%@ include file="../../AdminFooter.jsp" %>
