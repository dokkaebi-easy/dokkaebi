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
  clock: string;
}

export default function Cube({ time, color, clock }: CubeProps) {
  const [random, SetRandom] = useState(10);

  useEffect(() => {
    SetRandom(Math.random() * 5 + 20);
  }, []);

  return (
    <Cubestyled random={random}>
      <CubeTopstyled color={color} />
      <CubeDivstyled>
        <CubeSpanstyled color={color} number={0}>
          <CubeTimestyled>
            {time} {clock}
          </CubeTimestyled>
          <CubeTimestyled>
            {time} {clock}
          </CubeTimestyled>
        </CubeSpanstyled>
        <CubeSpanstyled color={color} number={1}>
          <CubeTimestyled>
            {time} {clock}
          </CubeTimestyled>
          <CubeTimestyled>
            {time} {clock}
          </CubeTimestyled>
        </CubeSpanstyled>
        <CubeSpanstyled color={color} number={2}>
          <CubeTimestyled>
            {time} {clock}
          </CubeTimestyled>
          <CubeTimestyled>
            {time} {clock}
          </CubeTimestyled>
        </CubeSpanstyled>
        <CubeSpanstyled color={color} number={3}>
          <CubeTimestyled>
            {time} {clock}
          </CubeTimestyled>
          <CubeTimestyled>
            {time} {clock}
          </CubeTimestyled>
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
  width: 75px;
  height: 75px;
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
  width: 75px;
  height: 75px;
  background: #eee;
  transform: rotateX(90deg) translateZ(37px);
`;

const CubeBottomstyled = styled.div<CubeColorProps>`
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 75px;
  height: 75px;
  background: rgb(${(props) => props.color});
  transform: rotateX(90deg) translateZ(-75px);
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
  transform: rotateY(${(props) => props.number * 90}deg) translateZ(37px);
  display: flex;
  justify-content: center;
  align-items: center;
`;

const CubeTimestyled = styled.h2`
  position: absolute;
  font-size: 2em;
  color: #fff;
  transform: translateZ(15px);

  &:nth-child(1) {
    transform: translateZ(0) translateY(10px);
    color: rgba(0, 0, 0, 0.1);
    filter: blur(2px);
  }
`;
