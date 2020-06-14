import React, { useState } from 'react';
import { Label } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import { useHistory } from 'react-router-dom';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { ContentCard } from '../../shared/Dashboard';
import { createSeatClass } from '../../../api/seatClasses';
import { route } from '../../../routing';
import SeatClassForm from './SeatClassForm';
import { seatClassColors } from '../../../constants';

const AddSeatClass = () => {
    const { t } = useTranslation();
    const history = useHistory();
    const [error, setError] = useState(false);
    const makeCancellable = useCancellablePromise();

    const handleCreate = async (values) => {
        try {
            await makeCancellable(createSeatClass(values));
            history.push(route('manager.seatClasses.list'));
        } catch (err) {
            setError(err);
        }
    };
    return (
        <ContentCard fluid>
            <Label attached="top">{t('Add seat class')}</Label>
            <SeatClassForm
                name=""
                price=""
                color={seatClassColors[0]}
                benefits={[]}
                existingBenefits={[]}
                handleSubmit={handleCreate}
                error={error}
                benefitsButtonName="Add existing benefits"
            />
        </ContentCard>
    );
};

export default AddSeatClass;
