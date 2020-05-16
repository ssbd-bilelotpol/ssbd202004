import { compile } from 'path-to-regexp';
import matchPath from 'react-router/modules/matchPath';
import { routes } from './constants';

export const route = (name, variables) => {
    if (!name) return undefined;

    const parts = name.split('.');
    let path = '';
    let currentNode = routes;

    parts.forEach((part, index) => {
        currentNode = currentNode[part];
        path += currentNode.path;

        if (index !== parts.length - 1) {
            currentNode = currentNode.subroutes;
        }
    });

    if (variables) {
        return compile(path)(variables);
    }
    return path;
};

export const matchRoutes = (pagePath) => {
    let currentNode = Object.values(routes);
    let fullRoute = '';
    let fullPath = '';
    const allNodes = [];

    while (currentNode) {
        let nextNode;
        for (const subNode of currentNode) {
            const matchResult = matchPath(pagePath, { path: fullRoute + subNode.path });
            if (matchResult) {
                nextNode = subNode;
                fullPath += compile(subNode.path)(matchResult.params);
                break;
            }
        }

        if (!nextNode) break;
        currentNode = nextNode;

        fullRoute += currentNode.path;
        allNodes.push({
            ...currentNode,
            fullRoute,
            fullPath,
        });

        if (!currentNode.subroutes) {
            break;
        }
        currentNode = Object.values(currentNode.subroutes);
    }

    return allNodes;
};
