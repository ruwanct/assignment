import React from 'react';
import {Box, Heading} from 'grommet';

const PlayerDetails = ({ playerId, playerName }) => (
    <Box justify="center" align="center" direction="row" gap="xlarge">
        <Box justify="center" align="center" direction="row">
            <Heading level="3" size="medium" margin="none" justify="center">
                Player Id: {playerId}
            </Heading>
        </Box>
        <Box justify="center" align="center" direction="row">
            <Heading level="3" size="medium" margin="none" justify="center">
                 Name: {playerName}   
            </Heading>
        </Box>
    </Box>
);


export default PlayerDetails;
