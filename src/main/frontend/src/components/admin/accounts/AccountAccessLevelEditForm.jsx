import { useTranslation } from 'react-i18next';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import React, { useState } from 'react';
import { Button, Form, Message, Segment } from 'semantic-ui-react';
import { FieldArray, Formik } from 'formik';
import styled from 'styled-components';
import { roleColors, roles } from '../../../constants';
import { AccountAccessLevelEditSchema } from '../../../yup';

const ToggleSegment = styled(({ backgroundColor, ...rest }) => <Segment {...rest} />)`
    &&& {
        border-top-color: ${(props) => props.backgroundColor};
        border-top-width: 6px;
    }
`;

const AccountAccessLevelEditForm = ({ onSave, onSuccess, onFail, loading, data }) => {
    const { t } = useTranslation();

    const makeCancellable = useCancellablePromise();

    const [savingError, setSavingError] = useState(false);
    const [saved, setSaved] = useState(false);

    const translate = (msg) => {
        if (msg.key) {
            return t(msg.key, msg.value);
        }

        return t(msg.key);
    };

    const handleSave = async (values) => {
        setSaved(false);
        setSavingError(false);

        try {
            await makeCancellable(onSave(values));
            setSaved(true);
            if (onSuccess) onSuccess();
        } catch (e) {
            setSavingError(e);
            if (onFail) onFail(e);
        }
    };

    return (
        <>
            {saved && <Message success content={t('Changes successfully saved')} />}
            {savingError && <Message negative content={t(savingError.message)} />}
            {!loading && (
                <Formik
                    initialValues={{
                        accessLevels: data.accessLevels,
                    }}
                    validationSchema={AccountAccessLevelEditSchema}
                    onSubmit={handleSave}
                >
                    {({ values, errors, handleSubmit, isSubmitting, handleChange }) => (
                        <Form onSubmit={handleSubmit}>
                            <Form.Group widths="equal">
                                <FieldArray
                                    name="accessLevels"
                                    render={(arrayHelpers) =>
                                        Object.values(roles).map((key) => (
                                            <Form.Field key={key}>
                                                <ToggleSegment backgroundColor={roleColors[key]}>
                                                    <Form.Checkbox
                                                        id={key}
                                                        error={
                                                            errors.accessLevels &&
                                                            !Array.isArray(errors.accessLevels) && {
                                                                content: translate(
                                                                    errors.accessLevels
                                                                ),
                                                                pointing: 'above',
                                                            }
                                                        }
                                                        name="accessLevels"
                                                        checked={values.accessLevels.includes(key)}
                                                        label={t(key)}
                                                        onChange={handleChange}
                                                        onClick={(e, props) => {
                                                            return props.checked
                                                                ? arrayHelpers.insert(key)
                                                                : arrayHelpers.remove(
                                                                      values.accessLevels.indexOf(
                                                                          key
                                                                      )
                                                                  );
                                                        }}
                                                        value={key}
                                                    />
                                                </ToggleSegment>
                                            </Form.Field>
                                        ))
                                    }
                                />
                            </Form.Group>
                            <Button
                                type="submit"
                                disabled={isSubmitting}
                                loading={isSubmitting}
                                id="saveAccessLevelsButton"
                            >
                                {t('Save')}
                            </Button>
                        </Form>
                    )}
                </Formik>
            )}
        </>
    );
};

export default AccountAccessLevelEditForm;
