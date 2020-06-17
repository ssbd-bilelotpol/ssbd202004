import React, { useState } from 'react';
import { Label, Message } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import { useHistory, useParams } from 'react-router-dom';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { ContentCard } from '../../shared/Dashboard';
import { deleteSeatClass, editSeatClass, useSeatClass } from '../../../api/seatClasses';
import { useTitle } from '../../Title';
import { useBreadcrumb } from '../../Breadcrumbs';
import SeatClassForm from './SeatClassForm';
import { route } from '../../../routing';

const EditSeatClass = () => {
    const { t } = useTranslation();
    const { name } = useParams();
    const { data, etag, loading, error, refetch } = useSeatClass(name);
    const makeCancellable = useCancellablePromise();
    const [savingError, setSavingError] = useState(false);
    const [deleteError, setDeleteError] = useState(false);
    const [deleteLoading, setDeleteLoading] = useState(false);
    const [saved, setSaved] = useState(false);

    const history = useHistory();

    useTitle(data.name ? `${data.name}` : '');
    useBreadcrumb({
        name: data.name ? `${data.name}` : '',
    });

    const handleSave = async (values) => {
        setSaved(false);
        setSavingError(false);

        try {
            await makeCancellable(editSeatClass(name, values, etag));
            setSaved(true);
            if (refetch) refetch();
        } catch (e) {
            setSavingError(e);
        }
    };

    const handleDelete = async () => {
        setDeleteError(false);
        setDeleteLoading(true);
        try {
            await makeCancellable(deleteSeatClass(data.name, etag));
            history.push(route('manager.seatClasses.list'));
        } catch (err) {
            setDeleteError(err);
            setDeleteLoading(false);
        }
    };

    return (
        <ContentCard fluid>
            <Label attached="top">{t('Edit seat class')}</Label>
            {!error ? (
                <>
                    {saved && <Message success content={t('Changes successfully saved')} />}
                    {savingError && <Message negative content={t(savingError.message)} />}
                    <SeatClassForm
                        name={data.name}
                        price={data.price}
                        color={data.color}
                        benefits={[]}
                        existingBenefits={data.benefits}
                        onSubmit={handleSave}
                        onDelete={handleDelete}
                        error={error}
                        benefitsButtonName="Edit existing benefits"
                        loading={loading}
                        edit
                        deleteError={deleteError}
                        deleteLoading={deleteLoading}
                    />
                </>
            ) : (
                <Message negative content={t(error.message)} />
            )}
        </ContentCard>
    );
};

export default EditSeatClass;
