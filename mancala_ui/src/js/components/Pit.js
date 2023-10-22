import React from "react";

import { Box, Card, CardBody, CardFooter, Heading, Text } from "grommet";

class Pit extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            pitId: props.pitId,
            playerId: props.playerId,
            stones: props.stones,
            name: props.pitName
        };
    }

    componentWillReceiveProps(nextProps, nextContext) {
      this.setState({stones: nextProps.stones})
  }

    render() {
        return <Card key={this.state.pitId}  onClick={() => { this.props.onClick(this.state) }} hoverIndicator={{
                    background: {
                      color: 'brand',
                    },
                    elevation: 'large',
                    
                  }} justify="center" background= {this.props.bg}>
                  <CardBody pad={{left:"large", right:"medium", top:"medium", bottom:"medium"}} justify="center" align="center" >
                    <Box width="large" height="xxsmall" justify="center" >
                      <Heading level="2" size="medium" margin="none" justify="center">
                        {this.state.stones}
                      </Heading>
                      </Box>
                  </CardBody>
                  <CardFooter pad={{ horizontal: 'medium', vertical: 'small' }} justify="center" background={{color : "#00000008"}}>
                      
                      <Text size="small">{this.state.name}</Text>
                  </CardFooter>
                  </Card>;
    }
}

export default Pit;
