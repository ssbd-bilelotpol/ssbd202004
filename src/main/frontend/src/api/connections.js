import { useGet } from './hooks';

export const useListConnectionsByCodePhrase = (phrase) => {
    return useGet(`/connections?phrase=${phrase}`, []);
};
