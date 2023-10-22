import React from "react";

import {Box, Button, Grid} from "grommet";
import Pit from "./Pit";
import InfoBar from "./InfoBar";
import PlayerDetails from "./PlayerDetails";

import OnBoarding from "./OnBoarding";

class Board extends React.Component {
    constructor(props) {
        super(props);
        this.onStartGameButtonClick = this.onStartGameButtonClick.bind(this);
        this.onPitClick = this.onPitClick.bind(this);
        this.getMessage = this.getMessage.bind(this);


        this.onStopButtonClick = this.onStopButtonClick.bind(this);
        this.onJoinGameButtonClick = this.onJoinGameButtonClick.bind(this)


        this.state = {
            started: false,
            loading: true,
            data: {},
            player1: "",
            player2: "",
            gameId: ""
        }
    }

    componentDidMount() {

    }

    onStartGameButtonClick(st) {
        if (!this.state.started && st.player1 && st.player2) {
            const url = process.env.REACT_APP_API_URL + '/game/start';
            var data = [st.player1, st.player2]
            this.setState({loading: true}, () => {
                const requestOptions = {
                    method: 'POST',
                    headers: {'content-type': 'application/json'},
                    body: JSON.stringify({"players": data})
                };
                fetch(url, requestOptions)
                    .then(response => {
                        if (response.ok)
                            return response.json()
                    })
                    .then(data => {
                        this.setState({started: true, loading: false, data: data});
                    });
            });

        }
    }


    onJoinGameButtonClick(st) {
        if (!this.state.started) {
            const url = process.env.REACT_APP_API_URL + '/game/join';

            this.setState({loading: true}, () => {
                const requestOptions = {
                    method: 'POST',
                    headers: {'content-type': 'application/json'},
                    body: JSON.stringify({"gameId": st.gameId})
                };
                fetch(url, requestOptions)
                    .then(response => {
                        if (response.ok)
                            return response.json()
                    })
                    .then(data => {
                        this.setState({started: true, loading: false, data: data});
                    });
            })

        }
    }

    onPitClick(e) {
        const url = process.env.REACT_APP_API_URL + '/game/move';
        this.setState({loading: true, error: null}, () => {
            let data = {
                "gameId": this.state.data ? this.state.data.gameId : -1,
                "pitId": e.pitId - 1,
                "playerId": e.playerId
            };

            const requestOptions = {
                method: 'POST',
                headers: {'content-type': 'application/json'},
                body: JSON.stringify(data)
            };
            fetch(url, requestOptions)
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    } else {
                        console.log("HTTP Error:", response.status, response.statusText);
                        throw new Error(`HTTP Error: ${response.status} ${response.statusText}`);
                    }
                })
                .then(data => {
                    this.setState({loading: false, data: data});
                })
                .catch(err => {
                    console.log(err);
                    this.setState({loading: false, error: "An error occurred. Please try again later."});
                });
        });
    }

    getMessage() {
        if (this.state.data.winner === undefined)
            return "Start New Game";
        else if (this.state.data.winner !== -1)
            return "Congratulations!!! Player " + (this.state.data.winner + 1) + " won";
        else if (this.state.data.winner === 2)
            return "The game is drawn !!";
        else
            var data = this.state.data;
        if (data.players)
            return "Current Player: " + (data.players[data.nextTurn].name);
        else
            return "Current Player:  Player " + (data.nextTurn + 1);
    }

    getPit(data) {
        let pit = [];

        for (let i = 5; i >= 0; i--) {
            pit.push(<Pit onClick={this.onPitClick}
                          pitId={i + 1}
                          playerId={0}
                          pitName={"Pit " + (i + 1)}
                          stones={data.board.pits ? data.board.pits[0][i].stoneCount : 0}
                          bg={{color: 'light-3'}}/>)
        }
        for (let i = 0; i < 6; i++) {
            pit.push(<Pit onClick={this.onPitClick}
                          pitId={i + 1}
                          playerId={1}
                          pitName={"Pit " + (i + 1)}
                          stones={data.board.pits ? data.board.pits[1][i].stoneCount : 0}
                          bg={{color: '#ffffff'}}/>)
        }
        return pit;
    }

    onStopButtonClick() {
        this.setState({started: false});
    }

    renderContent() {

        const {started, error} = this.state;
        var msg = this.getMessage();
        if (error) {
            var data = this.state.data;
            msg = "Invalid move! Current should be player: " + (data.players[data.nextTurn].name);
        }

        if (started) {
            var player1 = this.state.data.players[0].name;
            var player2 = this.state.data.players[1].name;
            return (<Box margin="none">

                <InfoBar message={msg} gameId={this.state.data.gameId}/>

                <PlayerDetails playerId="1" playerName={this.state.data.players ? player1 : "Player1"}/>
                <Box direction="row" gap="medium" justify="center" pad="medium" background={{color: "light-1"}}
                     elevation="medium" margin="medium">
                    <Box pad={{top: 'xlarge'}}>
                        <div id='rrr'>
                            <Pit stones={this.state.data.board.pits ? this.state.data.board.pits[0][6].stoneCount : 0}
                                 pitName="Player 1"
                                 playerId={0} bg={{color: 'light-3'}}/></div>
                    </Box>
                    <Box>
                        <Grid columns={{count: 6, size: 'auto'}} gap="medium" pad={{top: "medium", bottom: "medium"}}>
                            {this.getPit(this.state.data)}
                        </Grid>
                    </Box>
                    <Box pad={{top: 'xlarge'}}>
                        <Pit stones={this.state.data.board.pits ? this.state.data.board.pits[1][6].stoneCount : 0}
                             pitName="Player 2"
                             playerId={1} bg={{color: '#ffffff'}}/>
                    </Box>
                </Box>

                <PlayerDetails playerId="2" playerName={this.state.data.players ? player2 : "Player2"}/>
                <Box pad="large" direction="row" justify="center">
                    <Button
                        hoverIndicator="accent-2"
                        onClick={(event) => this.onStopButtonClick()}
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
                            Stop Game
                        </Box>
                    </Button>
                </Box>
            </Box>);
        } else {
            return <OnBoarding onStartGameButtonClick={this.onStartGameButtonClick}
                               onJoinGameButtonClick={this.onJoinGameButtonClick}/>;
        }
    }

    render() {
        return (<Box>{this.renderContent()}</Box>);
    }
}

export default Board;
