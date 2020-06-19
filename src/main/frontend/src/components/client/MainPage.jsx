import React, { useState } from 'react';
import { Image, Message, Label } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import SearchFlight from './purchase/SearchFlight';
import TopMenu from '../TopMenu';
import SelectFlight from './purchase/SelectFlight';
import { PageContainer } from '../shared/SimpleComponents';

const MainPage = () => {
    const { t } = useTranslation();
    const [searchQuery, setSearchQuery] = useState();

    return (
        <>
            <TopMenu clouds="bottom">
                <SearchFlight onSubmit={(values) => setSearchQuery(values)} />
            </TopMenu>

            {searchQuery ? (
                <SelectFlight searchQuery={searchQuery} />
            ) : (
                <PageContainer>
                    <Message warning>
                        <Label color="red" horizontal>
                            {t('Announcements')}
                        </Label>
                        {t('Bilelotpol is not restricted due to the coronavirus')}
                    </Message>
                    <Image fluid src={`${process.env.PUBLIC_URL}/banner.png`} />
                </PageContainer>
            )}
        </>
    );
};

export default MainPage;
