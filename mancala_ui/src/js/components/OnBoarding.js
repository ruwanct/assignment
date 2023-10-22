import React from 'react';
import {Accordion, AccordionPanel, Box, Button, Layer, PageHeader, Text, TextInput} from "grommet";

class OnBoarding extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            player1: "",
            player2: "",
            gameId: "",
            player1Error: "",
            player2Error: "",
            gameIdError: "",
            showPlayer1Error: false,
            showPlayer2Error: false,
            showGameIdError: false,
        };

        this.state = {
            activePanel: [0], // Set the index of the top panel to be open
        };
    }

    componentWillReceiveProps(nextProps, nextContext) {
        this.setState({stones: nextProps.stones});
    }

    handleSubmit = (event, type) => {
        event.preventDefault();

        // Input validation logic
        let isValid = true;

        if (type === "start" && !this.state.player1) {
            this.setState({player1Error: "Please fill in this field", showPlayer1Error: true});
            isValid = false;
        } else {
            this.setState({player1Error: "", showPlayer1Error: false});
        }

        if (type === "start" && !this.state.player2) {
            this.setState({player2Error: "Please fill in this field", showPlayer2Error: true});
            isValid = false;
        } else {
            this.setState({player2Error: "", showPlayer2Error: false});
        }

        if (type === "load" && (!this.state.gameId || isNaN(this.state.gameId))) {
            this.setState({gameIdError: "Please fill in this field", showGameIdError: true});
            isValid = false;
        } else {
            this.setState({gameIdError: "", showGameIdError: false});
        }

        if (isValid) {
            if (type === "start") {
                // Call your props function to start the game
                this.props.onStartGameButtonClick(this.state);
            } else if (type === "load") {
                // Call your props function to load the game
                this.props.onJoinGameButtonClick(this.state);
            }
        }
    };

    render() {
        return (
            <Accordion activeIndex={this.state.activePanel}
                       onActive={newIndex => this.setState({activePanel: newIndex})}>
                <AccordionPanel label="NEW GAME">
                    <Box>
                        <Box justify="center" align="center" direction="row" gap="medium">
                            <Box justify="center" align="center" direction="row">
                                <PageHeader
                                    align={"center"}
                                    justify={"center"}
                                    title=" Enter players' names to start a new game "
                                />
                            </Box>
                        </Box>

                        <Box fill align="center" justify="start" margin={{top: "small"}}>
                            <Box width="medium" margin="small">
                                <TextInput
                                    placeholder="PLAYER 01 NAME"
                                    value={this.state.player1}
                                    onChange={evt => this.setState({player1: evt.target.value})}
                                />
                                {this.state.showPlayer1Error && (
                                    <Layer
                                        position="right"
                                        modal={false}
                                        onClickOutside={() => this.setState({showPlayer1Error: false})}
                                        responsive={false}
                                        plain
                                    >
                                        <Box background="status-error" pad="small" round="small">
                                            <Text color="white">{this.state.player1Error}</Text>
                                        </Box>
                                    </Layer>
                                )}
                            </Box>

                            <Box width="medium" margin="small">
                                <TextInput
                                    placeholder="PLAYER 02 NAME"
                                    value={this.state.player2}
                                    onChange={evt => this.setState({player2: evt.target.value})}
                                />
                                {this.state.showPlayer2Error && (
                                    <Layer
                                        position="right"
                                        modal={false}
                                        onClickOutside={() => this.setState({showPlayer2Error: false})}
                                        responsive={false}
                                        plain
                                    >
                                        <Box background="status-error" pad="small" round="small">
                                            <Text color="white">{this.state.player2Error}</Text>
                                        </Box>
                                    </Layer>
                                )}
                            </Box>
                        </Box>

                        <Box pad="none" direction="row" justify="center">
                            <Button
                                hoverIndicator="accent-2"
                                onClick={(event) => this.handleSubmit(event, "start")}
                                active
                                width="xlarge"
                            >
                                <Box
                                    pad="small"
                                    direction="row"
                                    justify="center"
                                    align="center"
                                    gap="small"
                                    width="medium"
                                    background={{color: 'lightblue', opacity: false}}
                                >
                                    Start Game
                                </Box>
                            </Button>
                        </Box>

                        <Box border="top" margin={{top: "medium"}}/>


                    </Box>
                </AccordionPanel>
                <AccordionPanel label="LOAD GAME">
                    <Box justify="center" align="start" direction="row" gap="medium">
                        <Box justify="center" align="center" direction="row">
                            <PageHeader
                                align={"center"}
                                justify={"center"}
                                title=" Enter game ID to load a saved game "
                            />
                        </Box>
                    </Box>
                    <Box>
                        <Box fill align="center" justify="start" margin={{bottom: "small"}}>
                            <Box width="medium" margin="small">
                                <TextInput
                                    placeholder="Game ID"
                                    value={this.state.gameId}
                                    value={this.state.gameId}
                                    onChange={evt => this.setState({gameId: evt.target.value})}
                                />
                                {this.state.showGameIdError && (
                                    <Layer
                                        position="right"
                                        modal={false}
                                        onClickOutside={() => this.setState({showGameIdError: false})}
                                        responsive={false}
                                        plain
                                    >
                                        <Box background="status-error" pad="small" round="small">
                                            <Text color="white">{this.state.gameIdError}</Text>
                                        </Box>
                                    </Layer>
                                )}
                            </Box>
                        </Box>

                        <Box pad="none" direction="row" justify="center">
                            <Button
                                hoverIndicator="accent-2"
                                onClick={(event) => this.handleSubmit(event, "load")}
                                active
                                width="large"
                            >
                                <Box
                                    pad="small"
                                    direction="row"
                                    justify="center"
                                    align="center"
                                    gap="small"
                                    width="medium"
                                    background={{color: 'lightblue', opacity: false}}
                                >
                                    <Text>Load Game</Text>
                                </Box>
                            </Button>
                        </Box>
                    </Box>
                    <Box border="top" margin={{top: "medium"}}/>
                </AccordionPanel>
            </Accordion>
        );
    }
}

export default OnBoarding;
