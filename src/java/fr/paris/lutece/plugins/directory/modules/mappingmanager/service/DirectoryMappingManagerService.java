/*
 * Copyright (c) 2002-2016, Mairie de Paris
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
package fr.paris.lutece.plugins.directory.modules.mappingmanager.service;

import fr.paris.lutece.plugins.directory.business.IEntry;
import fr.paris.lutece.plugins.directory.service.DirectoryPlugin;
import fr.paris.lutece.plugins.directory.utils.DirectoryUtils;
import fr.paris.lutece.plugins.notifygru.modules.directory.NotifyGruDirectoryManager;
import fr.paris.lutece.plugins.workflow.modules.notifygru.service.AbstractServiceProvider;
import fr.paris.lutece.plugins.workflow.modules.notifygru.service.ServiceConfigTaskForm;
import fr.paris.lutece.portal.service.admin.AdminUserService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


public class DirectoryMappingManagerService
{
    private static String PARAM_MAPING_NONE = "Aucun";

    /** The _str key. */
    private static String STRING_KEY = "notifygru-directory.ProviderService.@.";

    public static ReferenceList getListProvider(  )
    {
        ReferenceList refenreceList = new ReferenceList(  );

        for ( NotifyGruDirectoryManager provider : SpringContextService.getBeansOfType( NotifyGruDirectoryManager.class ) )
        {
            if ( provider.isManagerProvider(  ) )
            {
                provider.updateListProvider(  );

                refenreceList.addAll( provider.buildReferenteListProvider(  ) );
            }
        }

        return refenreceList;
    }

    public static ReferenceList getListEntryOfProvider( String strKey )
    {
        ReferenceList refenreceList = new ReferenceList(  );
        refenreceList.addItem( 0, PARAM_MAPING_NONE );

        if ( ServiceConfigTaskForm.isBeanExists( strKey ) )
        {
            AbstractServiceProvider provider = ServiceConfigTaskForm.getCustomizedBean( strKey );
            refenreceList.addAll( ( (NotifyGruDirectoryManager) provider ).getReferenteListEntityProvider(  ) );
        }

        return refenreceList;
    }

    /**
      * Method to get directory entries list
      * @param nIdDirectory id directory
      * @param request request
      * @return ReferenceList entries list
      */
    public static ReferenceList getListEntries( String strKey, HttpServletRequest request )
    {
        int nIdDirectory = Integer.parseInt( strKey.split( STRING_KEY )[1] );

        if ( nIdDirectory != -1 )
        {
            Plugin pluginDirectory = PluginService.getPlugin( DirectoryPlugin.PLUGIN_NAME );
            List<IEntry> listEntries = DirectoryUtils.getFormEntries( nIdDirectory, pluginDirectory,
                    AdminUserService.getAdminUser( request ) );
            ReferenceList referenceList = new ReferenceList(  );

            for ( IEntry entry : listEntries )
            {
                if ( entry.getEntryType(  ).getComment(  ) )
                {
                    continue;
                }

                if ( entry.getEntryType(  ).getGroup(  ) )
                {
                    if ( entry.getChildren(  ) != null )
                    {
                        for ( IEntry child : entry.getChildren(  ) )
                        {
                            if ( child.getEntryType(  ).getComment(  ) )
                            {
                                continue;
                            }

                            ReferenceItem referenceItem = new ReferenceItem(  );
                            referenceItem.setCode( String.valueOf( child.getPosition(  ) ) );
                            referenceItem.setName( child.getTitle(  ) );
                            referenceList.add( referenceItem );
                        }
                    }
                }
                else
                {
                    ReferenceItem referenceItem = new ReferenceItem(  );
                    referenceItem.setCode( String.valueOf( entry.getPosition(  ) ) );
                    referenceItem.setName( entry.getTitle(  ) );
                    referenceList.add( referenceItem );
                }
            }

            return referenceList;
        }
        else
        {
            return new ReferenceList(  );
        }
    }
}
