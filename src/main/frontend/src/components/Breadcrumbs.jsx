import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useLocation } from 'react-router-dom';
import { Breadcrumb, Card } from 'semantic-ui-react';
import styled from 'styled-components';
import { useTranslation } from 'react-i18next';
import { matchRoutes } from '../routing';
import { setBreadcrumbVariable } from '../actions/breadcrumbs';

const BreadcrumbsCard = styled(Card)`
    &&& {
        padding: 15px;
        margin-top: 15px;
    }
`;

export const useBreadcrumb = (variables) => {
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(setBreadcrumbVariable(variables));
    }, [variables, dispatch]);
};

export const Breadcrumbs = () => {
    const { t } = useTranslation();
    const { breadcrumbs } = useSelector((state) => state.breadcrumbs);
    const { pathname } = useLocation();

    const sections = matchRoutes(pathname)
        .filter((x) => x.breadcrumb !== undefined)
        .map((x) => {
            const variables = x.breadcrumb.matchAll(/{([A-z]+)\|?([A-z0-9 ]+)?}/g);

            let displayName = t(x.breadcrumb);
            for (const variable of variables) {
                const [fullMatch, variableName, defaultValue] = variable;
                const variableValue = breadcrumbs.variables[variableName] || t(defaultValue);
                displayName = displayName.replace(fullMatch, variableValue);
            }

            return {
                key: displayName,
                content: displayName,
                as: Link,
                to: x.breadcrumbLink !== false ? x.fullPath : '#',
            };
        });

    return (
        <BreadcrumbsCard fluid>
            <Breadcrumb icon="right angle" sections={sections} />
        </BreadcrumbsCard>
    );
};
