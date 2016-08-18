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
package fr.paris.lutece.plugins.directory.modules.mappingmanager.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * This class provides Data Access methods for DirectoryMappingManagerDAO objects
 */
public final class DirectoryMappingManagerDAO implements IDirectoryMappingManagerDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_mappingmanager ) FROM directory_mapping_manager";
    private static final String SQL_QUERY_SELECT = "SELECT id_mappingmanager, beanKey, guid, reference_demand,customer_id, mobilePhoneNumber, fixedPhoneNumber, email FROM directory_mapping_manager WHERE id_mappingmanager = ?";
    private static final String SQL_QUERY_SELECT_BY_KEY = "SELECT id_mappingmanager, beanKey, guid, reference_demand, customer_id, mobilePhoneNumber, fixedPhoneNumber, email FROM directory_mapping_manager WHERE beanKey = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO directory_mapping_manager ( id_mappingmanager, beanKey, guid, reference_demand, customer_id, mobilePhoneNumber, fixedPhoneNumber, email ) VALUES (?, ?, ?, ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM directory_mapping_manager WHERE id_mappingmanager = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE directory_mapping_manager SET id_mappingmanager = ?, beanKey = ?, guid = ?, reference_demand = ?,customer_id = ?, mobilePhoneNumber = ?, fixedPhoneNumber = ?, email = ? WHERE id_mappingmanager = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_mappingmanager, beanKey, guid, reference_demand, customer_id, mobilePhoneNumber, fixedPhoneNumber, email FROM directory_mapping_manager";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_mappingmanager FROM directory_mapping_manager";

    /**
     * Generates a new primary key
     * @param plugin The Plugin
     * @return The new primary key
     */
    public int newPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, plugin );
        daoUtil.executeQuery(  );

        int nKey = 1;

        if ( daoUtil.next(  ) )
        {
            nKey = daoUtil.getInt( 1 ) + 1;
        }

        daoUtil.free(  );

        return nKey;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( DirectoryMappingManager notifygruMappingManager, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );

        notifygruMappingManager.setId( newPrimaryKey( plugin ) );

        int nIndex = 1;
        daoUtil.setInt( nIndex++, notifygruMappingManager.getId(  ) );

        daoUtil.setString( nIndex++, notifygruMappingManager.getBeanKey(  ) );
        daoUtil.setInt( nIndex++, notifygruMappingManager.getGuid(  ) );
        daoUtil.setInt( nIndex++, notifygruMappingManager.getReferenceDemand(  ) );
        daoUtil.setInt( nIndex++, notifygruMappingManager.getCustomerId(  ) );
        daoUtil.setInt( nIndex++, notifygruMappingManager.getMobilePhoneNumber(  ) );
        daoUtil.setInt( nIndex++, notifygruMappingManager.getFixedPhoneNumber(  ) );
        daoUtil.setInt( nIndex++, notifygruMappingManager.getEmail(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public DirectoryMappingManager load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeQuery(  );

        DirectoryMappingManager notifygruMappingManager = null;

        if ( daoUtil.next(  ) )
        {
            int nIndex = 1;
            notifygruMappingManager = new DirectoryMappingManager(  );
            notifygruMappingManager.setId( daoUtil.getInt( nIndex++ ) );
            notifygruMappingManager.setBeanKey( daoUtil.getString( nIndex++ ) );

            notifygruMappingManager.setGuid( daoUtil.getInt( nIndex++ ) );
            notifygruMappingManager.setReferenceDemand( daoUtil.getInt( nIndex++ ) );
            notifygruMappingManager.setCustomerId( daoUtil.getInt( nIndex++ ) );

            notifygruMappingManager.setMobilePhoneNumber( daoUtil.getInt( nIndex++ ) );
            notifygruMappingManager.setFixedPhoneNumber( daoUtil.getInt( nIndex++ ) );
            notifygruMappingManager.setEmail( daoUtil.getInt( nIndex++ ) );
        }

        daoUtil.free(  );

        return notifygruMappingManager;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public DirectoryMappingManager load( String strKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_KEY, plugin );
        daoUtil.setString( 1, strKey );
        daoUtil.executeQuery(  );

        DirectoryMappingManager notifygruMappingManager = null;

        if ( daoUtil.next(  ) )
        {
            int nIndex = 1;
            notifygruMappingManager = new DirectoryMappingManager(  );
            notifygruMappingManager.setId( daoUtil.getInt( nIndex++ ) );
            notifygruMappingManager.setBeanKey( daoUtil.getString( nIndex++ ) );
            notifygruMappingManager.setGuid( daoUtil.getInt( nIndex++ ) );
            notifygruMappingManager.setReferenceDemand( daoUtil.getInt( nIndex++ ) );
            notifygruMappingManager.setCustomerId( daoUtil.getInt( nIndex++ ) );
            notifygruMappingManager.setMobilePhoneNumber( daoUtil.getInt( nIndex++ ) );
            notifygruMappingManager.setFixedPhoneNumber( daoUtil.getInt( nIndex++ ) );
            notifygruMappingManager.setEmail( daoUtil.getInt( nIndex++ ) );
        }

        daoUtil.free(  );

        return notifygruMappingManager;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1, nKey );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( DirectoryMappingManager notifygruMappingManager, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );

        int nIndex = 1;
        daoUtil.setInt( nIndex++, notifygruMappingManager.getId(  ) );
        daoUtil.setString( nIndex++, notifygruMappingManager.getBeanKey(  ) );
        daoUtil.setInt( nIndex++, notifygruMappingManager.getGuid(  ) );
        daoUtil.setInt( nIndex++, notifygruMappingManager.getReferenceDemand(  ) );
        daoUtil.setInt( nIndex++, notifygruMappingManager.getCustomerId(  ) );
        daoUtil.setInt( nIndex++, notifygruMappingManager.getMobilePhoneNumber(  ) );
        daoUtil.setInt( nIndex++, notifygruMappingManager.getFixedPhoneNumber(  ) );
        daoUtil.setInt( nIndex++, notifygruMappingManager.getEmail(  ) );
        daoUtil.setInt( nIndex, notifygruMappingManager.getId(  ) );

        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<DirectoryMappingManager> selectDirectoryMappingManagersList( Plugin plugin )
    {
        List<DirectoryMappingManager> notifygruMappingManagerList = new ArrayList<DirectoryMappingManager>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            DirectoryMappingManager notifygruMappingManager = new DirectoryMappingManager(  );
            int nIndex = 1;
            notifygruMappingManager.setId( daoUtil.getInt( nIndex++ ) );
            notifygruMappingManager.setBeanKey( daoUtil.getString( nIndex++ ) );
            notifygruMappingManager.setGuid( daoUtil.getInt( nIndex++ ) );
            notifygruMappingManager.setReferenceDemand( daoUtil.getInt( nIndex++ ) );

            notifygruMappingManager.setCustomerId( daoUtil.getInt( nIndex++ ) );
            notifygruMappingManager.setMobilePhoneNumber( daoUtil.getInt( nIndex++ ) );
            notifygruMappingManager.setFixedPhoneNumber( daoUtil.getInt( nIndex++ ) );
            notifygruMappingManager.setEmail( daoUtil.getInt( nIndex++ ) );

            notifygruMappingManagerList.add( notifygruMappingManager );
        }

        daoUtil.free(  );

        return notifygruMappingManagerList;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdDirectoryMappingManagersList( Plugin plugin )
    {
        List<Integer> notifygruMappingManagerList = new ArrayList<Integer>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            notifygruMappingManagerList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free(  );

        return notifygruMappingManagerList;
    }
}
