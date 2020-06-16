import React, { useState } from 'react';
import { useParams } from 'react-router-dom';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { Label, Message } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import { useTitle } from '../../Title';
import { useAirplaneSchema, updateAirplaneSchema } from '../../../api/airplaneSchemas';
import { useBreadcrumb } from '../../Breadcrumbs';
import { ContentCard } from '../../shared/Dashboard';
import EditPlaneForm from './EditPlaneForm';
import { useSeatClasses } from '../../../api/seatClasses';

const EditPlane = () => {
    const { t } = useTranslation();
    const makeCancellable = useCancellablePromise();
    const { id } = useParams();
    const { data: airplaneSchema, etag, loading, error: fetchError, refetch } = useAirplaneSchema(
        id
    );
    const {
        data: seatClasses,
        loading: seatClassesLoading,
        error: seatClassesError,
    } = useSeatClasses();
    const [saved, setSaved] = useState(false);
    const [savingError, setSavingError] = useState(null);

    useTitle(airplaneSchema.name ? airplaneSchema.name : '');
    useBreadcrumb({ name: airplaneSchema.name ? airplaneSchema.name : '' });

    const handleSubmit = async (values) => {
        setSaved(false);
        setSavingError(null);
        try {
            await makeCancellable(updateAirplaneSchema(id, values, etag));
            setSaved(true);
            refetch();
        } catch (err) {
            setSavingError(err);
        }
    };

    return (
        <>
            <ContentCard fluid>
                <Label attached="top">{t('Edit plane')}</Label>
                {saved && !savingError && (
                    <Message success content={t('Airport updated successfully')} />
                )}
                {seatClassesError && <Message negative content={t(seatClassesError.message)} />}
                {fetchError && <Message negative content={t(fetchError.message)} />}
                {savingError && <Message negative content={t(savingError.message)} />}
                <EditPlaneForm
                    loading={loading || seatClassesLoading}
                    airplaneSchema={airplaneSchema}
                    seatClasses={seatClasses}
                    onSubmit={handleSubmit}
                    edit
                />
            </ContentCard>
        </>
    );
};

export default EditPlane;
