import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { Label, Message } from 'semantic-ui-react';
import { useHistory } from 'react-router-dom';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { ContentCard } from '../../shared/Dashboard';
import { generateSeats } from '../../shared/PlaneInput';
import { createAirplaneSchema } from '../../../api/airplaneSchemas';
import { route } from '../../../routing';
import { useSeatClasses } from '../../../api/seatClasses';
import EditPlaneForm from './EditPlaneForm';

const AddPlane = () => {
    const { t } = useTranslation();
    const history = useHistory();
    const [airplaneSchema, setAirplaneSchema] = useState(null);
    const [error, setError] = useState(null);
    const makeCancellable = useCancellablePromise();

    const handleSubmit = async (values) => {
        setError(false);
        try {
            await makeCancellable(createAirplaneSchema(values));
            setError(null);
            history.push(route('manager.planes.list'));
        } catch (err) {
            setError(err);
        }
    };

    const { data: seatClasses, loading, error: seatClassesError } = useSeatClasses();

    useEffect(() => {
        if (seatClassesError) {
            setError(seatClassesError);
            return;
        }

        if (seatClasses) {
            if (seatClasses.length === 0) {
                setError('There are no available seat classes. Add them first');
                return;
            }
            setAirplaneSchema({
                name: '',
                rows: 3,
                columns: 2,
                emptyRows: [],
                emptyColumns: [],
                seats: generateSeats(3, 2, seatClasses[0].name),
            });
        }
    }, [seatClasses, seatClassesError, setAirplaneSchema]);

    return (
        <ContentCard fluid>
            <Label attached="top">{t('Add plane')}</Label>
            {error && <Message negative content={t(error.message)} />}
            <EditPlaneForm
                loading={loading}
                airplaneSchema={airplaneSchema}
                onSubmit={handleSubmit}
                seatClasses={seatClasses}
                error={error}
            />
        </ContentCard>
    );
};

export default AddPlane;
