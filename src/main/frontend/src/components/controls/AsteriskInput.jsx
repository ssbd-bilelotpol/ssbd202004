import { Input } from 'semantic-ui-react';
import React from 'react';

const AsteriskInput = (props) => (
    <Input {...props} label={{ icon: 'asterisk' }} labelPosition="right corner" />
);

export default AsteriskInput;
