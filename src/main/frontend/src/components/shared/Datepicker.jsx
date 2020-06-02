import React, { useCallback } from 'react';
import styled from 'styled-components';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { Form, Label } from 'semantic-ui-react';
import { errorColor, errorLighterColor } from '../../constants';

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
    input.squished {
        border-bottom-left-radius: 0 !important;
        border-top-left-radius: 0 !important;
    }
`;

const FullWidthDiv = styled.div`
    &&& > * {
        width: 100%;
    }
`;

const DateInput = React.forwardRef(
    ({ value, onClick, onBlur, name, error, required, label, placeholderText }, ref) => {
        return (
            <FullWidthDiv onClick={onClick} ref={ref}>
                <SquishedInput
                    name={name}
                    value={value}
                    placeholder={placeholderText}
                    labelPosition="right corner"
                    control={SquishedInput}
                    onBlur={onBlur}
                    error={error}
                >
                    {label && (
                        <LeftSideLabel className={error && 'warning'} basic>
                            {label}
                        </LeftSideLabel>
                    )}
                    <input className={label && 'squished'} />
                    {required && <Label icon="asterisk" corner="right" />}
                </SquishedInput>
            </FullWidthDiv>
        );
    }
);

const SemanticDatePicker = ({
    error,
    onBlur,
    setFieldValue,
    name,
    label,
    placeholderText,
    ...props
}) => {
    const ref = React.createRef();
    const handleChange = useCallback((date) => setFieldValue && setFieldValue(name, date), [
        name,
        setFieldValue,
    ]);

    return (
        <FullWidthDiv>
            <DatePicker
                onBlur={onBlur}
                onChange={handleChange}
                name={name}
                {...props}
                customInput={
                    <DateInput
                        ref={ref}
                        error={error}
                        label={label}
                        placeholderText={placeholderText}
                    />
                }
            />
        </FullWidthDiv>
    );
};
export default SemanticDatePicker;
