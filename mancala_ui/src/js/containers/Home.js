import React from 'react';

import {Box} from 'grommet';
import Header from '../components/Header'
import Section from '../components/Section';
import Board from '../components/Board';
import MyFooter from '../components/MyFooter';

class Home extends React.Component {
    render() {
        return (
            <Box>
                <Section pad={{top: "large", bottom: "medium"}} background={{color: 'neutral-3'}}>
                    <Box direction="row-responsive" justify="start" gap="xlarge">
                        <Box width="xlarge" justify="center">
                            <Header label="Mancala" size="medium"/>
                        </Box>
                    </Box>
                </Section>
                <Section pad={{top: "medium"}}>
                    <Board/>
                </Section>
                <div  id='ruwan' style={{position: "absolute", width: '100%', bottom:'0'}}>
                    <Section width="100%" pad={"none"}>
                        <MyFooter/>
                    </Section>
                </div>
            </Box>
        )
    }
}

export default Home;
