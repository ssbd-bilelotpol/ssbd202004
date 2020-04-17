import React from 'react';
import Button from "@material-ui/core/Button";
import ListItem from "@material-ui/core/ListItem";
import {ListItemText} from "@material-ui/core";
import List from "@material-ui/core/List";
import makeStyles from "@material-ui/core/styles/makeStyles";
import {useSelector} from "react-redux";

const useStyles = makeStyles(theme => ({
    root: {
        marginTop: theme.spacing(3),
        width: '100%',
        maxWidth: 360,
        backgroundColor: theme.palette.background.paper,
    },
}));

const AuthTest = () => {
    const classes = useStyles();

    const server = `${process.env.REACT_APP_API_URL}`;
    const urls = [
        "/security/client",
        "/security/clientManager",
        "/security/resourceManager",
        "/security/admin"
    ];

    const [state, setState] = React.useState(urls.map(() => 'Unknown'));
    const user = useSelector(state => state.auth.user);

    const test = () => {
        Promise.all(urls.map(url => {
            return fetch(`${server}${url}`, {
                headers: {
                    'Authorization': `Bearer ${user.token}`,
                }
            })
                .then((response) => {
                    if (response.ok) {
                        return 'Authorized';
                    }

                    if (response.status === 403) {
                        return 'Forbidden';
                    } else if (response.status === 404) {
                        return 'Not found';
                    } else if (response.status === 401) {
                        return 'Unauthorized';
                    }
                })
                .catch(() => {
                    return 'No connection';
                });
        })).then(result => {
            setState(result);
        });
    };

    const handleTest = () => {
        test();
    };

    return <div className={classes.root}>
        <Button variant="outlined" color="primary" onClick={handleTest}>Sprawdź poziom dostępu</Button>
        <List>
            {urls.map((url, idx) => <ListItem key={url}>
                <ListItemText primary={url} secondary={state[idx]} secondaryTypographyProps={{color: state[idx] !== 'Authorized' ? "secondary" : "primary"}}/>
            </ListItem>)}
        </List>
    </div>
};

export default AuthTest;
