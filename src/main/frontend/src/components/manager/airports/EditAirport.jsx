import React from 'react';
import { useParams } from 'react-router-dom';
import { useTitle } from '../../Title';
import { useAirport, updateAirport } from '../../../api/airports';
import { useBreadcrumb } from '../../Breadcrumbs';
import EditAirportForm from './EditAirportForm';
import { ContentCard } from '../../shared/Dashboard';

const EditAirport = () => {
    const { code } = useParams();
    const { data, etag, loading, error, refetch } = useAirport(code);

    useTitle(data.name ? data.name : '');
    useBreadcrumb({ airportName: data.name ? data.name : '' });

    return (
        <>
            <ContentCard fluid>
                <EditAirportForm
                    airport={data}
                    loading={loading}
                    etag={etag}
                    refetch={refetch}
                    onSave={(values) => updateAirport(code, values, etag)}
                    fetchError={error}
                />
            </ContentCard>
        </>
    );
};

export default EditAirport;
