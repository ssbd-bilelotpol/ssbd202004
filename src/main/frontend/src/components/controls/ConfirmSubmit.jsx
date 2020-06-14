import React, { useState } from 'react';
import { Label, Button, Transition, Icon } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import styled from 'styled-components';

const ExtendingButton = styled(Button)`
    &&&[data-extended='false'] {
        border-top-right-radius: 0.28571429rem;
        border-bottom-right-radius: 0.28571429rem;
    }
`;

const ConfirmSubmit = (props) => {
    const {
        onSubmit,
        confirmMessage,
        cancelText,
        confirmText,
        children,
        labelPosition,
        negative,
        icon,
        ...otherProps
    } = props;
    const { t } = useTranslation();

    const [visible, setVisible] = useState(false);

    const show = () => setVisible(true);
    const hide = () => setVisible(false);
    const onConfirm = (e) => {
        e.preventDefault();
        e.stopPropagation();
        onSubmit();
        hide();
    };

    return (
        <div>
            <Button.Group>
                <ExtendingButton
                    type="button"
                    onClick={visible ? hide : show}
                    data-extended={visible}
                    {...props}
                    negative={!visible && negative}
                    labelPosition={icon && 'left'}
                >
                    {icon && <Icon name={icon} />}
                    {visible ? cancelText || t('Cancel') : children || t('Save')}
                </ExtendingButton>

                <Transition visible={visible} animation="fade" duration={500}>
                    <Button.Or text={t('or')} />
                </Transition>
                <Transition visible={visible} animation="fade right" duration={500}>
                    <Button {...otherProps} positive onClick={onConfirm} type="submit">
                        {confirmText || t('Confirm')}
                    </Button>
                </Transition>
            </Button.Group>

            <Transition visible={visible} animation="fade right" duration={500}>
                <Label color="red" pointing="left">
                    {confirmMessage || t('This action is irreversible')}
                </Label>
            </Transition>
        </div>
    );
};

export default ConfirmSubmit;
