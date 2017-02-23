/*
 * Copyright (c) 2002-2017, Mairie de Paris
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
import fr.paris.lutece.plugins.notifygru.modules.directory.services.INotifyGruDirectoryService;
import fr.paris.lutece.plugins.notifygru.modules.directory.services.NotifyGruDirectoryConstants;
import fr.paris.lutece.plugins.notifygru.modules.directory.services.provider.DirectoryProviderManager;
import fr.paris.lutece.plugins.workflow.modules.notifygru.service.provider.ProviderDescription;
import fr.paris.lutece.plugins.workflow.modules.notifygru.service.provider.ProviderManagerUtil;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;

import java.util.Collection;
import java.util.List;


public class DirectoryMappingManagerService
{
    private static String PARAM_MAPING_NONE = "Aucun";
    
    private static INotifyGruDirectoryService _notifyGruDirectoryService = SpringContextService.getBean( NotifyGruDirectoryConstants.BEAN_SERVICE_PROVIDER_DIRECTORY );

    public static ReferenceList getListProviders(  )
    {
        ReferenceList referenceList = new ReferenceList( );
        List<DirectoryProviderManager> listProviderManagers = SpringContextService.getBeansOfType( DirectoryProviderManager.class );

        for ( DirectoryProviderManager providerManager : listProviderManagers )
        {
            Collection<ProviderDescription> collectionProviderDescriptions = providerManager.getAllProviderDescriptions( );

            for ( ProviderDescription providerDescription : collectionProviderDescriptions )
            {
                referenceList.addItem( ProviderManagerUtil.buildCompleteProviderId( providerManager.getId( ), providerDescription.getId( ) ),
                        providerDescription.getLabel( ) );
            }
        }

        return referenceList;
    }

    /**
     * Gives a {@code ReferenceList} object containing the list of entries for the specified directory.
     * The code of the {@code ReferenceItem} corresponds to the position of the entry.
     * The name of the {@code ReferenceItem} corresponds to the title of the entry.    
     * @param nIdDirectory the directory id
     * @return the {@code ReferenceList} object
     */
    public static ReferenceList getEntryPositions( int nIdDirectory )
    {
        ReferenceList referenceList = new ReferenceList(  );
        referenceList.addItem( 0, PARAM_MAPING_NONE );
        
        List<IEntry> listRecordField = _notifyGruDirectoryService.getEntries( nIdDirectory );

        for ( IEntry entry : listRecordField )
        {
            ReferenceItem referenceItem = new ReferenceItem(  );
            referenceItem.setCode( String.valueOf( entry.getPosition(  ) ) );
            referenceItem.setName( entry.getTitle(  ) );
            referenceList.add( referenceItem );
        }

        return referenceList;
    }
}
