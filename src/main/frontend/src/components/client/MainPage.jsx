import React, { useState } from 'react';
import SearchFlight from './SearchFlight';
import TopMenu from '../TopMenu';
import SelectFlight from './SelectFlight';

const MainPage = () => {
    const [searchQuery, setSearchQuery] = useState();

    return (
        <>
            <TopMenu clouds="bottom">
                <SearchFlight onSubmit={(values) => setSearchQuery(values)} />
            </TopMenu>

            {searchQuery && <SelectFlight searchQuery={searchQuery} />}
        </>
    );
};

export default MainPage;
