import React, { useState } from 'react';
import { Form, Label, Message, Button, Divider, Transition, TextArea } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import { Formik, FieldArray } from 'formik';
import styled from 'styled-components';
import { useHistory } from 'react-router-dom';
import useCancellablePromise from '@rodw95/use-cancelable-promise';
import { ContentCard } from '../../shared/Dashboard';
import { SeatClassSchema } from '../../../yup';
import AsteriskInput from '../../controls/AsteriskInput';
import ConfirmSubmit from '../../controls/ConfirmSubmit';
import { createSeatClass } from '../../../api/seatClasses';
import { route } from '../../../routing';
import { errorColor, errorLighterColor } from '../../../constants';

const AlignedFormGroup = styled(Form.Group)`
    &&& {
        align-items: flex-end;
    }
`;

const LeftSideLabel = styled(Label)`
    &&& {
        border-bottom-right-radius: 0 !important;
        border-top-right-radius: 0 !important;
        border-right-width: 0 !important;
    }

    &&&.warning {
        color: ${errorColor};
        border-color: ${errorLighterColor};
    }
`;

const SquishedInput = styled(Form.Input)`
    input {
        border-bottom-left-radius: 0 !important;
        border-top-left-radius: 0 !important;
    }
`;

const AddSeatClass = () => {
    const { t } = useTranslation();

    return (
        <ContentCard fluid>
            <Label attached="top">{t('Add seat class')}</Label>
            <SeatClassAddForm />
        </ContentCard>
    );
};

const SeatClassAddForm = () => {
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

    const translate = (msg) => {
        if (msg.key) {
            return t(msg.key, msg.value);
        }
        return t(msg);
    };

    return (
        <>
            <Formik
                initialValues={{
                    name: '',
                    price: '',
                    benefits: [],
                }}
                validationSchema={SeatClassSchema}
                onSubmit={handleCreate}
            >
                {({
                    values,
                    errors,
                    touched,
                    handleChange,
                    handleSubmit,
                    handleBlur,
                    isSubmitting,
                }) => (
                    <Form error={!!error} onSubmit={handleSubmit}>
                        <Message error content={error && t(error.message)} />
                        <AlignedFormGroup>
                            <Form.Input
                                width="12"
                                name="name"
                                fluid
                                placeholder={t('Class name')}
                                control={AsteriskInput}
                                onChange={handleChange}
                                onBlur={handleBlur}
                                value={values.name}
                                error={
                                    touched.name &&
                                    errors.name && {
                                        content: translate(errors.name),
                                        pointing: 'below',
                                    }
                                }
                            />
                            <SquishedInput
                                width={4}
                                name="price"
                                placeholder={t('Price')}
                                labelPosition="right corner"
                                onChange={handleChange}
                                onBlur={handleBlur}
                                value={values.price}
                                error={
                                    touched.price &&
                                    errors.price && {
                                        content: translate(errors.price),
                                        pointing: 'below',
                                    }
                                }
                            >
                                <LeftSideLabel
                                    className={touched.price && errors.price && 'warning'}
                                    basic
                                >
                                    PLN
                                </LeftSideLabel>
                                <input type="number" step="0.01" />
                                <Label icon="asterisk" corner="right" />
                            </SquishedInput>
                        </AlignedFormGroup>
                        <FieldArray
                            name="benefits"
                            render={(arrayHelpers) => (
                                <>
                                    <Divider horizontal>
                                        <Button
                                            basic
                                            type="button"
                                            floated="right"
                                            onClick={() =>
                                                arrayHelpers.push({
                                                    name: '',
                                                    description: '',
                                                })
                                            }
                                        >
                                            {t('Add benefit')}
                                        </Button>
                                    </Divider>
                                    <Transition.Group>
                                        {values.benefits.map((benefit, index) => (
                                            // eslint-disable-next-line react/no-array-index-key
                                            <div key={index}>
                                                <AlignedFormGroup>
                                                    <Form.Input
                                                        width="4"
                                                        name={`benefits[${index}].name`}
                                                        fluid
                                                        placeholder={t('Benefit name')}
                                                        control={AsteriskInput}
                                                        onChange={handleChange}
                                                        onBlur={handleBlur}
                                                        value={values.benefits[index].name}
                                                        error={
                                                            touched.benefits &&
                                                            touched.benefits[index] &&
                                                            touched.benefits[index].name &&
                                                            errors.benefits &&
                                                            errors.benefits[index] &&
                                                            errors.benefits[index].name && {
                                                                content: translate(
                                                                    errors.benefits[index].name
                                                                ),
                                                                pointing: 'below',
                                                            }
                                                        }
                                                    />
                                                    <SquishedInput width="12">
                                                        <TextArea
                                                            name={`benefits[${index}].description`}
                                                            rows={1}
                                                            placeholder={t('Description')}
                                                            onChange={handleChange}
                                                            onBlur={handleBlur}
                                                            value={
                                                                values.benefits[index].description
                                                            }
                                                            error={
                                                                touched.benefits &&
                                                                touched.benefits[index] &&
                                                                touched.benefits[index]
                                                                    .description &&
                                                                errors.benefits &&
                                                                errors.benefits[index] &&
                                                                errors.benefits[index]
                                                                    .description && {
                                                                    content: translate(
                                                                        errors.benefits[index]
                                                                            .description
                                                                    ),
                                                                    pointing: 'below',
                                                                }
                                                            }
                                                        />
                                                        <Label
                                                            icon="asterisk"
                                                            size="mini"
                                                            corner="right"
                                                        />
                                                    </SquishedInput>
                                                    <Button
                                                        type="button"
                                                        color="red"
                                                        onClick={() => arrayHelpers.remove(index)}
                                                    >
                                                        {t('Remove')}
                                                    </Button>
                                                </AlignedFormGroup>
                                            </div>
                                        ))}
                                    </Transition.Group>
                                </>
                            )}
                        />
                        <ConfirmSubmit
                            onSubmit={handleSubmit}
                            disabled={isSubmitting}
                            loading={isSubmitting}
                        >
                            {t('Save')}
                        </ConfirmSubmit>
                    </Form>
                )}
            </Formik>
        </>
    );
};

export default AddSeatClass;
