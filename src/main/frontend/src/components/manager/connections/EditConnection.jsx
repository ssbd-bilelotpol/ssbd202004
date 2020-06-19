import React from 'react';
import { useParams } from 'react-router-dom';
import { useTitle } from '../../Title';
import { ContentCard } from '../../shared/Dashboard';
import { updateConnection, useConnection } from '../../../api/connections';
import EditConnectionForm from './EditConnectionForm';
import { useBreadcrumb } from '../../Breadcrumbs';

const EditConnection = () => {
    const { id } = useParams();
    const { data, etag, loading, error, refetch } = useConnection(id);

    useTitle(data.name ? data.name : '');
    useBreadcrumb({
        name: data.source ? `${data.source.name} - ${data.destination.name}` : '',
    });

    return (
        <>
            <ContentCard fluid>
                <EditConnectionForm
                    connection={data}
                    loading={loading}
                    etag={etag}
                    refetch={refetch}
                    onSave={(values) => updateConnection(id, values, etag)}
                    fetchError={error}
                />
            </ContentCard>
        </>
    );
};

export default EditConnection;
