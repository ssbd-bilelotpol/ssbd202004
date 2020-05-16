import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { matchRoutes } from '../routing';

export const useTitle = (title) => {
    const { pathname } = useLocation();
    const { t } = useTranslation();

    useEffect(() => {
        const route = matchRoutes(pathname).pop();
        const parts = [];

        if (title) {
            parts.push(title);
        }

        if (route.title) {
            parts.push(t(route.title));
        }

        parts.push('Bilelotpol');
        document.title = parts.join(' - ');
    }, [title, pathname, t]);
};
