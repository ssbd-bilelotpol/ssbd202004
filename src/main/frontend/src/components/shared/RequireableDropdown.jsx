import React from 'react';
import styled from 'styled-components';
import { Form, Label } from 'semantic-ui-react';

const DropdownLabel = styled(Label)`
    &&& {
        position: absolute;
        right: 1px;
        top: 1px;
        font-size: 0.64285714em;
        overflow: hidden;
        border-radius: 0 0.28571429rem 0 0;
    }
`;

const LabeledDropdownWithoutAsterisk = styled(Form.Dropdown)`
    &&& {
        justify-content: flex-start;
        .icon {
            right: 10px !important;
            left: auto !important;
            margin-left: 0 !important;
        }
        .loading {
            .icon {
                left: 0 !important;
                right: auto !important;
                margin-left: 0 !important;
            }
            .text.filtered,
            .text.default {
                padding-left: 1em !important;
            }
            input {
                padding-left: 2em !important;
            }
        }
        .disabled {
            opacity: 1 !important;
        }
    }

    &&&.disabled {
        opacity: 1 !important;
    }

    .active:not(.loading) .dropdown.icon {
        display: none;
    }
`;

const LabeledDropdownWithAsterisk = styled(LabeledDropdownWithoutAsterisk)`
    &&& {
        .icon {
            right: 30px !important;
            left: auto !important;
            margin-left: 0 !important;
        }
    }
`;

const StyledField = styled(Form.Field)`
    &&&.disabled {
        z-index: 99 !important;
        opacity: 1 !important;
    }
`;

const DropdownControl = ({ required, ...props }) => {
    return (
        <div style={{ position: 'relative' }}>
            {required && <DropdownLabel icon="asterisk" corner="right" />}
            {required ? (
                <LabeledDropdownWithAsterisk search selection {...props} />
            ) : (
                <LabeledDropdownWithoutAsterisk search selection {...props} />
            )}
        </div>
    );
};
const RequireableDropdown = ({ error, ...props }) => {
    return <StyledField error={error} control={DropdownControl} {...props} />;
};

export default RequireableDropdown;
