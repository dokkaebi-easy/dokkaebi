import React from 'react';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import styled, { keyframes } from 'styled-components';
import { Link } from 'react-router-dom';

export default function Create() {
  const handleOpenSource = () => {
    alert('준비중...');
  };
  return (
    <Box minHeight="85vh" sx={{ display: 'flex' }}>
      <Grid
        container
        direction="row"
        justifyContent="center"
        alignItems="center"
        spacing={10}
      >
        <Grid item>
          <Cardstyled>
            <Link to="/setting/0">
              <CardBoxstyled>
                <CardContentstyled>
                  <Typography
                    variant="h1"
                    sx={{
                      position: 'absolute',
                      top: '-10px',
                      right: '10px',
                      opacity: 0.2,
                    }}
                    fontFamily="Jua"
                    color="primary"
                  >
                    01
                  </Typography>
                  <Typography fontSize={40} color="primary" fontFamily="Jua">
                    일반
                  </Typography>
                  <Typography fontSize={40} color="primary" fontFamily="Jua">
                    프로젝트
                  </Typography>
                </CardContentstyled>
              </CardBoxstyled>
            </Link>
          </Cardstyled>
        </Grid>
        <Grid item>
          <Cardstyled>
            <CardBoxstyled onClick={handleOpenSource}>
              <CardContentstyled>
                <Typography
                  variant="h1"
                  sx={{
                    position: 'absolute',
                    top: '-10px',
                    right: '10px',
                    opacity: 0.2,
                  }}
                  fontFamily="Jua"
                  color="primary"
                >
                  02
                </Typography>
                <Typography fontSize={40} color="primary" fontFamily="Jua">
                  오픈소스
                </Typography>
                <Typography fontSize={40} color="primary" fontFamily="Jua">
                  프로젝트
                </Typography>
              </CardContentstyled>
            </CardBoxstyled>
          </Cardstyled>
        </Grid>
      </Grid>
    </Box>
  );
}

const Cardstyled = styled.div`
  position: relative;
  min-width: 300px;
  height: 300px;
  box-shadow: inset 5px 5px 5px rgba(0, 0, 0, 0.2),
    inset 5px 5px 5px rgba(255, 255, 255, 0.1),
    inset 5px 5px 5px rgba(0, 0, 0, 0.3),
    inset 5px 5px 5px rgba(255, 255, 255, 0.1);
  border-radius: 15px;
  margin: 30px;
`;

const CardBoxstyled = styled.div`
  position: absolute;
  top: 20px;
  left: 20px;
  bottom: 20px;
  right: 20px;

  display: flex;
  justify-content: center;
  align-items: center;

  border-radius: 15px;
  box-shadow: 0 20px 50px rgba(0, 0, 0, 0.5);
  transition: 0.5s;
  &: hover {
    transform: translateY(-50px);
    box-shadow: 0 40px 70px rgba(0, 0, 0, 0.5);
  } ;
`;

const CardContentstyled = styled.div`
  padding: 20px;
  text-align: center;
`;
