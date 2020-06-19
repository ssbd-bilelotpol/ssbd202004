import React from 'react';
import { useParams } from 'react-router-dom';
import { useTitle } from '../../Title';
import { useBreadcrumb } from '../../Breadcrumbs';
import { ContentCard } from '../../shared/Dashboard';
import EditFlightForm from './EditFlightForm';
import { updateFlight, useFlight } from '../../../api/flights';

const EditFlight = () => {
    const { code } = useParams();
    const { data, etag, loading, error, refetch } = useFlight(code);

    useTitle(data.code ? data.code : '');
    useBreadcrumb({ flightCode: data.code ? data.code : '' });

    return (
        <>
            <ContentCard fluid>
                <EditFlightForm
                    flight={data}
                    loading={loading}
                    etag={etag}
                    refetch={refetch}
                    onSave={(values) => updateFlight(code, values, etag)}
                    fetchError={error}
                />
            </ContentCard>
        </>
    );
};

export default EditFlight;
