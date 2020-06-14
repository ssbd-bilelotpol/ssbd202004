import React from 'react';
import { useTranslation } from 'react-i18next';
import { seatClassColors } from '../../constants';
import RequireableDropdown from './RequireableDropdown';

const ColorPicker = ({ value, onChange }) => {
    const { t } = useTranslation();

    const seatClassColorOptions = seatClassColors.map((color) => ({
        key: color,
        text: t(`${color}`),
        value: color,
        label: { color: color.toLowerCase(), empty: true, circular: true },
    }));

    return (
        <RequireableDropdown
            asterisk
            name="color"
            placeholder={t('Color')}
            selection
            value={value}
            onChange={(e, v) => onChange(v)}
            options={seatClassColorOptions}
        />
    );
};

export default ColorPicker;
