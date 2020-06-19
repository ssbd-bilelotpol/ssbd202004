import React from 'react';
import styled from 'styled-components';
import { Button, Icon } from 'semantic-ui-react';
import { getLetterOfAlphabet } from '../../utils';

const rowColToDigit = (seat, columns) => seat.row * columns + seat.col;

const Seat = styled.div`
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    font-size: 1.5rem;
    width: 50px;
    height: 50px;
    border-radius: 15px 15px 5px 5px;
    background: ${(props) =>
        (props.reserved && '#A0A0A0') || (props.selected && '#016936') || props.color};
    border: solid 1px
        ${(props) => (props.reserved && '#A0A0A0') || (props.selected && '#016936') || props.color};
    &&&:hover {
        cursor: pointer;
    }
    &&& .icon {
        font-size: 3rem;
        display: flex;
        justify-content: center;
        align-items: center;
        margin: 0;
    }
`;

const Cell = styled.div`
    &&& {
        width: 50px;
        height: 50px;
        display: flex;
        justify-content: center;
        align-items: center;
        ${(props) => props.row && `grid-row: ${props.row};`}
        ${(props) => props.column && `grid-column: ${props.column};`}
    }
`;

const PlaneGrid = styled.div`
    display: grid;
    grid-template-rows: repeat(${(props) => props.rows}, ${(props) => props.size});
    grid-template-columns: repeat(${(props) => props.columns}, ${(props) => props.size});
    grid-auto-flow: dense;
    grid-gap: 5px;
    justify-content: center;

    &&& .tiny.button {
        font-size: 0.7rem;
        margin-left: 4px;
    }
`;

const EmptyColumn = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    grid-column: ${(props) => props.col};
    grid-row: 1 / span ${(props) => props.length};
`;

const EmptyRow = styled(({ row, length, ...rest }) => <div {...rest} />)`
    display: flex;
    flex-direction: row;
    align-items: center;
    grid-row: ${(props) => props.row};
    grid-column: 1 / span ${(props) => props.length};
`;

const emptyBefore = (empties, num) => empties.filter((e) => e < num).length;

const range = (end, start = 1) =>
    Array(end - start + 1)
        .fill()
        .map((_, idx) => start + idx);

const colors = {
    RED: '#B03060',
    ORANGE: '#FE9A76',
    YELLOW: '#FFD700',
    OLIVE: '#32CD32',
    GREEN: '#016936',
    TEAL: '#008080',
    BLUE: '#0E6EB8',
    VIOLET: '#EE82EE',
    PURPLE: '#B413EC',
    PINK: '#FF1493',
    BROWN: '#A52A2A',
    GREY: '#A0A0A0',
    BLACK: '#000000',
};

const Plane = ({
    airplaneSchema,
    onSeatClick,
    seatClasses,
    editable,
    onHeaderAdd,
    onHeaderRemove,
}) => {
    const { seats, columns, rows, emptyRows, emptyColumns } = airplaneSchema;
    seats.sort((a, b) => rowColToDigit(a, columns) - rowColToDigit(b, columns));

    const gridWidth = columns + emptyColumns.length + 1;
    const gridHeight = rows + emptyRows.length + 1;

    const seatClassesMap = {};
    for (const seatClass of seatClasses) {
        seatClassesMap[seatClass.name] = seatClass;
    }

    return (
        <div>
            <PlaneGrid columns={gridWidth} rows={gridHeight} size="50px">
                {emptyRows.map((row) => (
                    <React.Fragment key={row + 100}>
                        <Cell
                            key={row + 100}
                            row={row + 2 + emptyBefore(emptyRows, row)}
                            column={1}
                        >
                            {editable && (
                                <Button
                                    icon="remove"
                                    size="tiny"
                                    onClick={() => onHeaderRemove(row)}
                                />
                            )}
                        </Cell>
                        <EmptyRow
                            key={row}
                            row={row + 2 + emptyBefore(emptyRows, row)}
                            length={gridWidth}
                        />
                    </React.Fragment>
                ))}
                {emptyColumns.map((col) => (
                    <React.Fragment key={col + 200}>
                        <Cell
                            key={col + 200}
                            column={col + 2 + emptyBefore(emptyColumns, col)}
                            row={1}
                        >
                            {editable && (
                                <Button
                                    icon="remove"
                                    size="tiny"
                                    onClick={() => onHeaderRemove(0, col)}
                                />
                            )}
                        </Cell>
                        <EmptyColumn
                            key={col}
                            col={col + 2 + emptyBefore(emptyColumns, col)}
                            length={gridHeight}
                        />
                    </React.Fragment>
                ))}
                <Cell />
                {range(columns).map((x) => (
                    <Cell key={x + 100}>
                        {getLetterOfAlphabet(x)}
                        {editable && x !== columns && !emptyColumns.includes(x) && (
                            <Button
                                icon="arrow right"
                                size="tiny"
                                onClick={() => onHeaderAdd(0, x)}
                            />
                        )}
                    </Cell>
                ))}
                {range(rows).map((x) => (
                    <Cell column={1} key={x + 200}>
                        {x}
                        {editable && x !== rows && !emptyRows.includes(x) && (
                            <Button icon="arrow down" size="tiny" onClick={() => onHeaderAdd(x)} />
                        )}
                    </Cell>
                ))}
                {seats.map((seat) => (
                    <Seat
                        key={seat.id}
                        reserved={seat.reserved}
                        selected={seat.selected}
                        color={
                            seat.seatClass === undefined
                                ? 'rgba(220, 220, 220, 0.5)'
                                : colors[seatClassesMap[seat.seatClass].color]
                        }
                        onClick={() => onSeatClick(seat)}
                    >
                        <span>
                            {seat.reserved && <Icon name="close" />}
                            {seat.selected && `${seat.row}${getLetterOfAlphabet(seat.col)}`}
                        </span>
                    </Seat>
                ))}
            </PlaneGrid>
        </div>
    );
};

export default Plane;
