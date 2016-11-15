/*
 * Copyright (c) 2002-2015, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.directory.modules.mappingmanager.web;

import fr.paris.lutece.plugins.directory.modules.mappingmanager.business.DirectoryMappingManager;
import fr.paris.lutece.plugins.directory.modules.mappingmanager.business.DirectoryMappingManagerHome;
import fr.paris.lutece.plugins.directory.modules.mappingmanager.service.DirectoryMappingManagerService;
import fr.paris.lutece.plugins.directory.modules.mappingmanager.web.rs.Constants;
import fr.paris.lutece.plugins.workflow.modules.notifygru.service.AbstractServiceProvider;
import fr.paris.lutece.plugins.workflow.modules.notifygru.service.ServiceConfigTaskForm;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.url.UrlItem;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * This class provides the user interface to manage NotifygruMappingManager features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "DirectoryMappingManagers.jsp", controllerPath = "jsp/admin/plugins/directory/modules/mappingmanager/", right = "DIRECTORYMAPPINGMANAGER_MANAGEMENT" )
public class DirectoryMappingManagerJspBean extends ManageDirectoryMappingmanagerJspBean
{
    ////////////////////////////////////////////////////////////////////////////
    // Constants

    // templates
    private static final String TEMPLATE_MANAGE_NOTIFYGRUMAPPINGMANAGERS = "/admin/plugins/directory/modules/mappingmanager/manage_directory_mappingmanager.html";
    private static final String TEMPLATE_CREATE_NOTIFYGRUMAPPINGMANAGER = "/admin/plugins/directory/modules/mappingmanager/create_directory_mappingmanager.html";
    private static final String TEMPLATE_MODIFY_NOTIFYGRUMAPPINGMANAGER = "/admin/plugins/directory/modules/mappingmanager/modify_directory_mappingmanager.html";

    // Parameters
    private static final String PARAMETER_ID_NOTIFYGRUMAPPINGMANAGER = "id";
    private static final String PARAMS_REQUEST_BEAN_KEY = "beankey";
    private static final String PARAMS_REQUEST_EMAIL = "email";
    private static final String PARAMS_REQUEST_MOBILE_PHONE_NUMBER = "mobilephonenumber";
    private static final String PARAMS_REQUEST_FIXED_PHONE_NUMBER = "fixedphonenumber";
    private static final String PARAMS_REQUEST_GUID = "guid";
    private static final String PARAMS_REQUEST_CUID = "cuid";
    private static final String PARAMS_REQUEST_REFERENCE_DEMAND = "referencedemand";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_NOTIFYGRUMAPPINGMANAGERS = "mappingmanager.manage_notifygrumappingmanagers.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_NOTIFYGRUMAPPINGMANAGER = "mappingmanager.modify_notifygrumappingmanager.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_NOTIFYGRUMAPPINGMANAGER = "mappingmanager.create_notifygrumappingmanager.pageTitle";

    // Markers
    private static final String MARK_NOTIFYGRUMAPPINGMANAGER_LIST = "mappingmanager_list";
    private static final String MARK_NOTIFYGRUMAPPINGMANAGER = "mappingmanager";
    private static final String MARK_NOTIFYGRU_FORM_LIST_PROVIDER = "list_provider";
    private static final String MARK_NOTIFYGRU_FORM_LIST_POSITION = "list_position";
    private static final String MARK_BASE_URL = "BASE_URL";
    private static final String MARK_KEY_AJAX = "AJAX_KEY";
    private static final String JSP_MANAGE_NOTIFYGRUMAPPINGMANAGERS = "jsp/admin/plugins/directory/modules/mappingmanager/DirectoryMappingManagers.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_NOTIFYGRUMAPPINGMANAGER = "mappingmanager.message.confirmRemoveNotifygruMappingManager";
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "mappingmanager.model.entity.notifygrumappingmanager.attribute.";

    // Views
    private static final String VIEW_MANAGE_NOTIFYGRUMAPPINGMANAGERS = "manageNotifygruMappingManagers";
    private static final String VIEW_CREATE_NOTIFYGRUMAPPINGMANAGER = "createNotifygruMappingManager";
    private static final String VIEW_MODIFY_NOTIFYGRUMAPPINGMANAGER = "modifyNotifygruMappingManager";

    // Actions
    private static final String ACTION_CREATE_NOTIFYGRUMAPPINGMANAGER = "createNotifygruMappingManager";
    private static final String ACTION_MODIFY_NOTIFYGRUMAPPINGMANAGER = "modifyNotifygruMappingManager";
    private static final String ACTION_REMOVE_NOTIFYGRUMAPPINGMANAGER = "removeNotifygruMappingManager";
    private static final String ACTION_CONFIRM_REMOVE_NOTIFYGRUMAPPINGMANAGER = "confirmRemoveNotifygruMappingManager";

    // Infos
    private static final String INFO_NOTIFYGRUMAPPINGMANAGER_CREATED = "mappingmanager.info.notifygrumappingmanager.created";
    private static final String INFO_NOTIFYGRUMAPPINGMANAGER_UPDATED = "mappingmanager.info.notifygrumappingmanager.updated";
    private static final String INFO_NOTIFYGRUMAPPINGMANAGER_REMOVED = "mappingmanager.info.notifygrumappingmanager.removed";

    // Session variable to store working values
    private DirectoryMappingManager _directorymappingmanager;

    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_NOTIFYGRUMAPPINGMANAGERS, defaultView = true )
    public String getManageNotifygruMappingManagers( HttpServletRequest request )
    {
        _directorymappingmanager = null;

        List<DirectoryMappingManager> listNotifygruMappingManagers = DirectoryMappingManagerHome.getDirectoryMappingManagersList(  );
        Map<String, Object> model = getPaginatedListModel( request, MARK_NOTIFYGRUMAPPINGMANAGER_LIST,
                listNotifygruMappingManagers, JSP_MANAGE_NOTIFYGRUMAPPINGMANAGERS );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_NOTIFYGRUMAPPINGMANAGERS, TEMPLATE_MANAGE_NOTIFYGRUMAPPINGMANAGERS,
            model );
    }

    /**
     * Returns the form to create a directorymappingmanager
     *
     * @param request The Http request
     * @return the html code of the directorymappingmanager form
     */
    @View( VIEW_CREATE_NOTIFYGRUMAPPINGMANAGER )
    public String getCreateNotifygruMappingManager( HttpServletRequest request )
    {
        _directorymappingmanager = ( _directorymappingmanager != null ) ? _directorymappingmanager
                                                                        : new DirectoryMappingManager(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_NOTIFYGRUMAPPINGMANAGER, _directorymappingmanager );
        model.put( MARK_NOTIFYGRU_FORM_LIST_PROVIDER, DirectoryMappingManagerService.getListProvider(  ) );

        UrlItem url = new UrlItem( AppPathService.getBaseUrl( request ) );

        model.put( MARK_NOTIFYGRU_FORM_LIST_POSITION, new ReferenceList(  ) );
        model.put( MARK_BASE_URL, url );
        model.put( MARK_KEY_AJAX, Constants.KEY_BEAN );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_NOTIFYGRUMAPPINGMANAGER, TEMPLATE_CREATE_NOTIFYGRUMAPPINGMANAGER,
            model );
    }

    /**
     * Process the data capture form of a new directorymappingmanager
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_NOTIFYGRUMAPPINGMANAGER )
    public String doCreateNotifygruMappingManager( HttpServletRequest request )
    {
        _directorymappingmanager = ( _directorymappingmanager != null ) ? _directorymappingmanager
                                                                        : new DirectoryMappingManager(  );

        populate( request );

        // Check constraints
        if ( !validateBean( _directorymappingmanager, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_NOTIFYGRUMAPPINGMANAGER );
        }

        if ( exitMapping(  ) )
        {
            addError( "modulenotifygrumappingmanager.validation.notifygrumappingmanager.BeanKey.exist", getLocale(  ) );

            return redirectView( request, VIEW_CREATE_NOTIFYGRUMAPPINGMANAGER );
        }

        DirectoryMappingManagerHome.create( _directorymappingmanager );
        addInfo( INFO_NOTIFYGRUMAPPINGMANAGER_CREATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_NOTIFYGRUMAPPINGMANAGERS );
    }

    protected void populate( HttpServletRequest request )
    {
        try
        {
            int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_NOTIFYGRUMAPPINGMANAGER ) );
            _directorymappingmanager.setId( nId );
        }
        catch ( NumberFormatException nfe )
        {
            AppLogService.info( "insertion of mapping" );
        }

        _directorymappingmanager.setBeanKey( request.getParameter( PARAMS_REQUEST_BEAN_KEY ) );
        _directorymappingmanager.setEmail( Integer.parseInt( request.getParameter( PARAMS_REQUEST_EMAIL ) ) );
        _directorymappingmanager.setMobilePhoneNumber( Integer.parseInt( request.getParameter( 
                    PARAMS_REQUEST_MOBILE_PHONE_NUMBER ) ) );
        _directorymappingmanager.setFixedPhoneNumber( Integer.parseInt( request.getParameter( 
                    PARAMS_REQUEST_FIXED_PHONE_NUMBER ) ) );

        _directorymappingmanager.setGuid( Integer.parseInt( request.getParameter( PARAMS_REQUEST_GUID ) ) );
        _directorymappingmanager.setReferenceDemand( Integer.parseInt( request.getParameter( 
                    PARAMS_REQUEST_REFERENCE_DEMAND ) ) );
        _directorymappingmanager.setCustomerId( Integer.parseInt( request.getParameter( PARAMS_REQUEST_CUID ) ) );
    }

    private Boolean exitMapping(  )
    {
        //not dublicate
        if ( DirectoryMappingManagerHome.findByPrimaryKey( _directorymappingmanager.getBeanKey(  ) ) != null )
        {
            return true;
        }

        return false;
    }

    /**
     * Manages the removal form of a directorymappingmanager whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_NOTIFYGRUMAPPINGMANAGER )
    public String getConfirmRemoveNotifygruMappingManager( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_NOTIFYGRUMAPPINGMANAGER ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_NOTIFYGRUMAPPINGMANAGER ) );
        url.addParameter( PARAMETER_ID_NOTIFYGRUMAPPINGMANAGER, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request,
                MESSAGE_CONFIRM_REMOVE_NOTIFYGRUMAPPINGMANAGER, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a notifygrumappingmanager
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage notifygrumappingmanagers
     */
    @Action( ACTION_REMOVE_NOTIFYGRUMAPPINGMANAGER )
    public String doRemoveNotifygruMappingManager( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_NOTIFYGRUMAPPINGMANAGER ) );
        DirectoryMappingManagerHome.remove( nId );
        addInfo( INFO_NOTIFYGRUMAPPINGMANAGER_REMOVED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_NOTIFYGRUMAPPINGMANAGERS );
    }

    /**
     * Returns the form to update info about a notifygrumappingmanager
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_NOTIFYGRUMAPPINGMANAGER )
    public String getModifyNotifygruMappingManager( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_NOTIFYGRUMAPPINGMANAGER ) );

        if ( ( _directorymappingmanager == null ) || ( _directorymappingmanager.getId(  ) != nId ) )
        {
            _directorymappingmanager = DirectoryMappingManagerHome.findByPrimaryKey( nId );
        }

        ReferenceList refenreceListBean = new ReferenceList(  );
        ReferenceList listPosition = new ReferenceList(  );

        if ( ( _directorymappingmanager != null ) &&
                ServiceConfigTaskForm.isBeanExists( _directorymappingmanager.getBeanKey(  ) ) )
        {
            AbstractServiceProvider _notifyGruService = ServiceConfigTaskForm.getCustomizedBean( _directorymappingmanager.getBeanKey(  ) );

            if ( ( _notifyGruService != null ) && _notifyGruService.isManagerProvider(  ) )
            {
                ReferenceList rListInstance = _notifyGruService.buildReferenteListProvider(  );

                for ( ReferenceItem provider : rListInstance )
                {
                    AppLogService.debug( "\n\n\n\n\n\n  provider.getCode() = " + provider.getCode(  ) +
                        "    _notifygrumappingmanager.getBeanKey() = " + _directorymappingmanager.getBeanKey(  ) );

                    if ( provider.getCode(  ).equals( _directorymappingmanager.getBeanKey(  ) ) )
                    {
                        refenreceListBean.add( provider );
                        listPosition = DirectoryMappingManagerService.getListEntryOfProvider( provider.getCode(  ) );
                    }
                }
            }
        }

        Map<String, Object> model = getModel(  );
        model.put( MARK_NOTIFYGRUMAPPINGMANAGER, _directorymappingmanager );

        model.put( MARK_NOTIFYGRU_FORM_LIST_PROVIDER, refenreceListBean );
        model.put( MARK_NOTIFYGRU_FORM_LIST_POSITION, listPosition );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_NOTIFYGRUMAPPINGMANAGER, TEMPLATE_MODIFY_NOTIFYGRUMAPPINGMANAGER,
            model );
    }

    /**
     * Process the change form of a notifygrumappingmanager
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_NOTIFYGRUMAPPINGMANAGER )
    public String doModifyNotifygruMappingManager( HttpServletRequest request )
    {
        populate( request );

        // Check constraints
        if ( !validateBean( _directorymappingmanager, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_NOTIFYGRUMAPPINGMANAGER, PARAMETER_ID_NOTIFYGRUMAPPINGMANAGER,
                _directorymappingmanager.getId(  ) );
        }

        DirectoryMappingManagerHome.update( _directorymappingmanager );
        addInfo( INFO_NOTIFYGRUMAPPINGMANAGER_UPDATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_NOTIFYGRUMAPPINGMANAGERS );
    }
}
