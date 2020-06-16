import Slider from 'rc-slider';
import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Dropdown, Card } from 'semantic-ui-react';
import styled from 'styled-components';
import Plane from './Plane';

export const generateSeats = (rows, columns, seatClass) => {
    const seats = [];
    for (let row = 1; row <= rows; row += 1) {
        for (let col = 1; col <= columns; col += 1) {
            seats.push({
                id: row * columns + col,
                row,
                col,
                reserved: false,
                selected: false,
                visible: true,
                seatClass,
            });
        }
    }
    return seats;
};

const convertToSeatData = (seats) => {
    const seatData = {};
    for (const seat of seats) {
        seatData[`${seat.row}|${seat.col}`] = {
            id: seat.id,
            row: seat.row,
            col: seat.col,
            seatClass: seat.seatClass,
        };
    }

    return seatData;
};

const SlidersContainer = styled.div`
    display: flex;
    align-items: center;
    margin-bottom: 1em;

    &&& .ui.fluid.card {
        padding: 0.82em;
        padding-left: 21px;
        padding-right: 21px;
        margin-top: 5px;
    }
`;

const GrowingSlider = styled.div`
    flex: 1;
    &&&:not(:first-child) {
        margin-left: 30px;
    }
`;

const LabeledDropdown = styled(Dropdown)`
    margin-top: 5px;
`;

const PlaneInput = ({ value, seatClasses, onChange }) => {
    const { t } = useTranslation();
    const { columns, rows, emptyRows, emptyColumns, seats } = value;
    const [seatData, setSeatData] = useState(convertToSeatData(seats));
    const [activeSeatClass, setActiveSeatClass] = useState(seatClasses[0].name);
    const handleHeaderAdd = (row, col) => {
        if (col) {
            onChange({
                ...value,
                emptyColumns: [...emptyColumns, col],
            });
        } else if (row) {
            onChange({
                ...value,
                emptyRows: [...emptyRows, row],
            });
        }
    };

    const seatClassOptions = seatClasses.map(({ name, color }) => ({
        key: name,
        text: name,
        value: name,
        label: { color: color.toLowerCase(), empty: true, circular: true },
    }));

    const handleHeaderRemove = (row, col) => {
        if (col) {
            onChange({
                ...value,
                emptyColumns: emptyColumns.filter((n) => n !== col),
            });
        } else if (row) {
            onChange({
                ...value,
                emptyRows: emptyRows.filter((n) => n !== row),
            });
        }
    };

    const handleSeatClick = (seat) => {
        setSeatData({
            ...seatData,
            [`${seat.row}|${seat.col}`]: {
                ...seatData[`${seat.row}|${seat.col}`],
                seatClass: activeSeatClass,
            },
        });
        onChange({
            ...value,
            seats: seats.map((s) => {
                if (s.row === seat.row && s.col === seat.col) {
                    return {
                        ...s,
                        seatClass: activeSeatClass,
                    };
                }
                return s;
            }),
        });
    };

    const createOutput = (r, c) => {
        const seats = [];
        const newSeatData = {
            ...seatData,
        };
        for (let row = 1; row <= r; row += 1) {
            for (let col = 1; col <= c; col += 1) {
                const key = `${row}|${col}`;
                if (!seatData[key] || ((row > rows || col > columns) && !seatData[key].id)) {
                    newSeatData[key] = {
                        row,
                        col,
                        seatClass: activeSeatClass,
                    };
                }
                seats.push({
                    ...newSeatData[key],
                    row,
                    col,
                });
            }
        }

        return [seats, newSeatData];
    };

    const handleColumnsChange = (n) => {
        const [seats, newSeatData] = createOutput(rows, n);
        setSeatData(newSeatData);
        onChange({
            ...value,
            columns: n,
            emptyColumns: emptyColumns.filter((col) => col !== n),
            seats,
        });
    };

    const handleRowsChange = (n) => {
        const [seats, newSeatData] = createOutput(n, columns);
        setSeatData(newSeatData);
        onChange({
            ...value,
            rows: n,
            emptyRows: emptyRows.filter((row) => row !== n),
            seats,
        });
    };

    const handleSeatClassChange = (_, { value }) => {
        setActiveSeatClass(value);
    };

    return (
        <>
            <SlidersContainer>
                <GrowingSlider>
                    <span>
                        {t('Columns')}: {columns}
                    </span>
                    <Card fluid>
                        <Slider min={2} max={9} onChange={handleColumnsChange} value={columns} />
                    </Card>
                </GrowingSlider>

                <GrowingSlider>
                    <span>
                        {t('Rows')}: {rows}
                    </span>
                    <Card fluid>
                        <Slider min={3} max={40} onChange={handleRowsChange} value={rows} />
                    </Card>
                </GrowingSlider>
            </SlidersContainer>

            <span>{t('Active seat class')}</span>
            <LabeledDropdown
                fluid
                placeholder={t('Active seat class')}
                selection
                options={seatClassOptions}
                value={activeSeatClass}
                onChange={handleSeatClassChange}
            />

            <Plane
                editable
                airplaneSchema={{
                    columns,
                    rows,
                    emptyColumns: emptyColumns || [],
                    emptyRows: emptyRows || [],
                    seats: generateSeats(rows, columns).map((seat) => ({
                        ...seat,
                        seatClass:
                            seatData[`${seat.row}|${seat.col}`] &&
                            seatData[`${seat.row}|${seat.col}`].seatClass,
                    })),
                }}
                seatClasses={seatClasses}
                onHeaderAdd={handleHeaderAdd}
                onHeaderRemove={handleHeaderRemove}
                onSeatClick={handleSeatClick}
            />
        </>
    );
};

export default PlaneInput;
