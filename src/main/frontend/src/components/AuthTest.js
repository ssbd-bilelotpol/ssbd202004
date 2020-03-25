import React from 'react';
import Button from "@material-ui/core/Button";
import ListItem from "@material-ui/core/ListItem";
import {ListItemText} from "@material-ui/core";
import List from "@material-ui/core/List";
import makeStyles from "@material-ui/core/styles/makeStyles";
import axios from "axios";
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
    const token = useSelector(state => state.auth.token);
    const authorities = useSelector(state => state.auth.authorities);

    const test = () => {
        axios.all(urls.map(url => {
            return axios.get(`${server}${url}`, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Authorization-Role': authorities && authorities[0]
                }
            })
                .then(() => 'Authorized')
                .catch(err => {
                    if (err.response) {
                        if (err.response.status === 403) {
                            return 'Forbidden';
                        } else if (err.response.status === 404) {
                            return 'Not found';
                        } else if (err.response.status === 401) {
                            return 'Unauthorized';
                        }
                        return 'Unknown error';
                    } else {
                        return 'No connection';
                    }
                });
        })).then(result => {
            setState(result);
        });
    };

    const handleTest = () => {
        test();
    };

    return <div className={classes.root}>
        <Button variant="outlined" color="primary" onClick={handleTest}>Perform access test</Button>
        <List>
            {urls.map((url, idx) => <ListItem key={url}>
                <ListItemText primary={url} secondary={state[idx]} secondaryTypographyProps={{color: state[idx] !== 'Authorized' ? "secondary" : "primary"}}/>
            </ListItem>)}
        </List>
    </div>
};

export default AuthTest;