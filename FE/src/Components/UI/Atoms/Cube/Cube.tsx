import React, { useEffect, useState } from 'react';
import styled, { keyframes } from 'styled-components';

interface CubeNumberProps {
  number: number;
  color: string;
}

interface CubeColorProps {
  color: string;
}

interface CubeRandomProps {
  random: number;
}

interface CubeProps {
  color: string;
  time: number;
}

export default function Cube({ time, color }: CubeProps) {
  const [random, SetRandom] = useState(10);

  useEffect(() => {
    SetRandom(Math.random() * 5 + 10);
  }, []);

  return (
    <Cubestyled random={random}>
      <CubeTopstyled color={color} />
      <CubeDivstyled>
        <CubeSpanstyled color={color} number={0}>
          <CubeClockstyled>{time}</CubeClockstyled>
          <CubeClockstyled>{time}</CubeClockstyled>
        </CubeSpanstyled>
        <CubeSpanstyled color={color} number={1}>
          <CubeClockstyled>{time}</CubeClockstyled>
          <CubeClockstyled>{time}</CubeClockstyled>
        </CubeSpanstyled>
        <CubeSpanstyled color={color} number={2}>
          <CubeClockstyled>{time}</CubeClockstyled>
          <CubeClockstyled>{time}</CubeClockstyled>
        </CubeSpanstyled>
        <CubeSpanstyled color={color} number={3}>
          <CubeClockstyled>{time}</CubeClockstyled>
          <CubeClockstyled>{time}</CubeClockstyled>
        </CubeSpanstyled>
      </CubeDivstyled>
      <CubeBottomstyled color={color} />
    </Cubestyled>
  );
}

const animate = keyframes`
  0% {
    transform: rotateX(-30deg) rotateY(0deg);
  }
  100% {
    transform: rotateX(-30deg) rotateY(360deg);
  }
`;

const Cubestyled = styled.div<CubeRandomProps>`
  z-index: -1;
  position: relative;
  transform-style: preserve-3d;
  width: 150px;
  height: 150px;
  transform: rotateX(-30deg);
  animation: ${animate} ${(props) => props.random}s linear infinite;
`;
const CubeDivstyled = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  transform-style: preserve-3d;
`;
const CubeTopstyled = styled.div<CubeColorProps>`
  position: absolute;
  top: 0;
  left: 0;
  width: 150px;
  height: 150px;
  background: #eee;
  transform: rotateX(90deg) translateZ(75px);
`;

const CubeBottomstyled = styled.div<CubeColorProps>`
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 150px;
  height: 150px;
  background: rgb(${(props) => props.color});
  transform: rotateX(90deg) translateZ(-150px);
  filter: blur(20px);
  box-shadow: 0 0 120px rgba(${(props) => props.color}, 0.2)
    rgba(${(props) => props.color}, 0.4) rgba(${(props) => props.color}, 0.6)
    rgba(${(props) => props.color}, 0.8) rgba(${(props) => props.color}, 1);
`;

const CubeSpanstyled = styled(CubeDivstyled)<CubeNumberProps>`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(#eee, rgb(${(props) => props.color}));
  transform: rotateY(${(props) => props.number * 90}deg) translateZ(75px);
  display: flex;
  justify-content: center;
  align-items: center;
`;

const CubeClockstyled = styled.h2`
  position: absolute;
  font-size: 5em;
  color: #fff;
  transform: translateZ(25px);

  &:nth-child(1) {
    transform: translateZ(0) translateY(20px);
    color: rgba(0, 0, 0, 0.1);
    filter: blur(2px);
  }
`;
