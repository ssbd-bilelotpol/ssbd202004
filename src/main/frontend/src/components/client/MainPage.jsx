import React, { useState } from 'react';
import { Image, Message, Label, Menu, Icon } from 'semantic-ui-react';
import { useTranslation } from 'react-i18next';
import { NavLink } from 'react-router-dom';
import { useSelector } from 'react-redux';
import SearchFlight from './purchase/SearchFlight';
import TopMenu from '../TopMenu';
import SelectFlight from './purchase/SelectFlight';
import { PageContainer } from '../shared/SimpleComponents';
import { route } from '../../routing';
import { roles } from '../../constants';

const MenuItems = () => {
    const { t } = useTranslation();

    const role = useSelector((state) => state.auth.user.role);
    return (
        <>
            {role === roles.client && (
                <Menu.Item as={NavLink} to={route('panel.tickets')}>
                    <Icon name="calendar" />
                    {t('Dashboard')}
                </Menu.Item>
            )}
        </>
    );
};

const MainPage = () => {
    const { t } = useTranslation();
    const [searchQuery, setSearchQuery] = useState();

    return (
        <>
            <TopMenu clouds="bottom" menuItems={MenuItems}>
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
