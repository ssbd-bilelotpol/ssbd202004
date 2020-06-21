import styled from 'styled-components';
import { Container, Header } from 'semantic-ui-react';

export const PageContainer = styled(Container)`
    &&& {
        margin-top: 25px;
    }
`;

export const BlueHeader = styled(Header)`
    color: rgb(1, 90, 130) !important;
`;

export const PageContent = styled.div`
    min-height: 100%;
    margin-bottom: -42px;
    padding-bottom: 70px;
`;
