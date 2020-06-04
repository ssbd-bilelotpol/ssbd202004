import { Popup, Icon, Loader } from 'semantic-ui-react';
import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { useTranslation } from 'react-i18next';
import { useCurrentAccountAuthInfo } from '../../api/profiles';

const LastAuth = () => {
    const { t } = useTranslation();
    const loggedIn = useSelector((state) => state.auth.loggedIn);
    const { data, error, loading } = useCurrentAccountAuthInfo();
    const [visible, setVisible] = useState(false);

    useEffect(() => {
        if (new Date(data.lastSuccessAuth) < new Date(data.lastIncorrectAuth)) {
            setVisible(true);
            setTimeout(() => setVisible(false), 5000);
        }
    }, [data]);

    const handleOpen = () => {
        setVisible(true);
    };

    const handleClose = () => {
        setVisible(false);
    };

    return (
        <>
            {loggedIn && !error && data.lastSuccessAuth && (
                <Popup
                    onMount={() => setVisible(true)}
                    open={visible}
                    onOpen={handleOpen}
                    onClose={handleClose}
                    trigger={!loading && <Icon name="history" />}
                    position="bottom center"
                    wide="very"
                >
                    <Popup.Content>
                        {data.lastSuccessAuth && (
                            <>
                                <h5>
                                    <Icon name="dot circle" color="green" />
                                    {t('Last correct login')}:
                                </h5>

                                <p>{new Date(data.lastSuccessAuth).toLocaleString()}</p>
                            </>
                        )}
                        {data.lastIncorrectAuth && (
                            <>
                                <h5>
                                    <Icon name="dot circle" color="red" />
                                    {t('Last incorrect login')}:{' '}
                                </h5>
                                <p>{new Date(data.lastIncorrectAuth).toLocaleString()}</p>
                            </>
                        )}
                    </Popup.Content>
                </Popup>
            )}
            {loading && <Loader active inline size="mini" />}
        </>
    );
};

export default LastAuth;
