import React from 'react';
import { Box, Heading } from 'grommet';
import Section from './Section';

const InfoBar = ({ message, gameId }) => (

    <Box>
        <Section pad={{ top: "small", bottom: "none" }}>
            <Box justify="center" align="center" direction="row" gap="xlarge">
                <Box justify="center" align="center" direction="row">
                    <Heading level="3" size="medium" margin="none" justify="center">
                        Game Id : {gameId}
                    </Heading>
                </Box>
            </Box>
        </Section>
        <Section pad={{ top: "small", bottom: "small" }}>
            <Box justify="center" align="center" direction="row" gap="xlarge">
                <Box justify="center" align="center" direction="row">
                    <Heading level="3" size="medium" margin="none" justify="center">
                        {message}
                    </Heading>
                </Box>
            </Box>
        </Section>
    </Box>
);

export default InfoBar;
